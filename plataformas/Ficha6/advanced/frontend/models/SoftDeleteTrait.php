<?php

namespace frontend\models;

/**
 * Soft delete: em vez de apagar o registo da BD, preenche a coluna deleted_at.
 * Para usar num modelo ActiveRecord: "use SoftDeleteTrait;" dentro da classe.
 * A tabela do modelo tem de ter a coluna deleted_at (ver migration).
 */
trait SoftDeleteTrait
{
    /**
     * Substitui o find() normal do ActiveRecord.
     * Todas as pesquisas passam a trazer SO os registos ativos (deleted_at IS NULL).
     *
     * Isto e automatico em todo o lado que usa find():
     *  - Book::find(), Book::findOne($id), Book::findAll()
     *  - os modelos Search (BookSearch, ChapterSearch, GenreSearch)
     *  - as relacoes ($book->chapters, $book->getChapters(), $chapter->book, ...)
     *  - a dropdown dos generos (Genre::find())
     *  - as regras 'exist' do rules() (nao deixa ligar a um genero/livro apagado)
     */
    public static function find()
    {
        //parent::find() e o find() original do ActiveRecord.
        //static::tableName() poe o nome da tabela antes da coluna (ex.: book.deleted_at)
        //para nao haver confusao se a query juntar (JOIN) varias tabelas
        return parent::find()->andWhere([static::tableName() . '.deleted_at' => null]);
    }

    /**
     * Pesquisa que inclui tambem os registos apagados.
     * (util se um dia quiseres uma pagina de "lixo" ou recuperar registos)
     */
    public static function findWithDeleted()
    {
        return parent::find();
    }

    /**
     * Marca o registo como apagado (nao o remove da BD).
     * @return bool true se gravou
     */
    public function softDelete()
    {
        //guarda a data/hora atual (timestamp) na coluna deleted_at
        $this->deleted_at = time();

        //save(false, [...]):
        //  false            -> nao corre as validacoes do rules()
        //  ['deleted_at']   -> so grava esta coluna (nao mexe nas outras)
        return $this->save(false, ['deleted_at']);
    }

    /**
     * Recupera um registo apagado (volta a ficar ativo).
     * @return bool true se gravou
     */
    public function restore()
    {
        //NULL = ativo outra vez
        $this->deleted_at = null;
        return $this->save(false, ['deleted_at']);
    }

    /**
     * Diz se o registo esta apagado.
     * @return bool
     */
    public function isDeleted()
    {
        return $this->deleted_at !== null;
    }
}