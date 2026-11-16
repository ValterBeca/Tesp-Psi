<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%chapter}}`.
 */
class m261105_203025_create_chapter_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%chapter}}', [
            'id' => $this->primaryKey(),
            'name' => $this->string(200)->notNull(),
            //FK: livro a que o capitulo pertence
            'book_id' => $this->integer()->notNull(),
        ], 'ENGINE=InnoDB');

        $this->createIndex('idx-chapter-book_id', '{{%chapter}}', 'book_id');

        //chapter.book_id -> book.id
        //CASCADE: apagar o livro apaga os capitulos
        $this->addForeignKey('fk-chapter-book_id', '{{%chapter}}', 'book_id', '{{%book}}', 'id', 'CASCADE');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropForeignKey('fk-chapter-book_id', '{{%chapter}}');
        $this->dropIndex('idx-chapter-book_id', '{{%chapter}}');
        $this->dropTable('{{%chapter}}');
    }
}
