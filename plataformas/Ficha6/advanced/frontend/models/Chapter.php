<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "chapter".
 *
 * @property int $id
 * @property string $name
 * @property int $book_id
 * @property int|null $deleted_at
 *
 * @property Book $book
 */
class Chapter extends \yii\db\ActiveRecord
{
    //soft delete: find() so traz livros ativos + metodo softDelete()
    use SoftDeleteTrait;


    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'chapter';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['name', 'book_id'], 'required'],
            [['book_id'], 'integer'],
            [['name'], 'string', 'max' => 200],
            [['book_id'], 'exist', 'skipOnError' => true, 'targetClass' => Book::class, 'targetAttribute' => ['book_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'name' => 'Name',
            'book_id' => 'Book ID',
        ];
    }

    /**
     * Gets query for [[Book]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getBook()
    {
        return $this->hasOne(Book::class, ['id' => 'book_id']);
    }

}
