<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "book".
 *
 * @property int $id
 * @property string $name
 * @property string $isbn
 * @property int $genre_id
 * @property int|null $deleted_at
 *
 * @property Chapter[] $chapters
 * @property Genre $genre
 */
class Book extends \yii\db\ActiveRecord
{
    //soft delete: find() so traz livros ativos + metodo softDelete()
    use SoftDeleteTrait;


    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'book';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['name', 'isbn', 'genre_id'], 'required'],
            [['genre_id'], 'integer'],
            [['name'], 'string', 'max' => 255],
            [['isbn'], 'string', 'max' => 80],
            [['genre_id'], 'exist', 'skipOnError' => true, 'targetClass' => Genre::class, 'targetAttribute' => ['genre_id' => 'id']],
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
            'isbn' => 'Isbn',
            'genre_id' => 'Genre ID',
        ];
    }

    /**
     * Gets query for [[Chapters]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getChapters()
    {
        return $this->hasMany(Chapter::class, ['book_id' => 'id']);
    }

    /**
     * Gets query for [[Genre]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getGenre()
    {
        return $this->hasOne(Genre::class, ['id' => 'genre_id']);
    }

}
