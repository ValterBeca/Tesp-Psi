<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%book}}`.
 */
class m261105_203017_create_book_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%book}}', [
            'id' => $this->primaryKey(),
            'name' => $this->string(255)->notNull(),
            'isbn' => $this->string(80)->notNull(),
            //FK: genero do livro
            'genre_id' => $this->integer()->notNull(),
        ], 'ENGINE=InnoDB');

        $this->createIndex('idx-book-genre_id', '{{%book}}', 'genre_id');

        //book.genre_id -> genre.id
        $this->addForeignKey('fk-book-genre_id', '{{%book}}', 'genre_id', '{{%genre}}', 'id', 'CASCADE');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropForeignKey('fk-book-genre_id', '{{%book}}');
        $this->dropIndex('idx-book-genre_id', '{{%book}}');
        $this->dropTable('{{%book}}');
    }
}
