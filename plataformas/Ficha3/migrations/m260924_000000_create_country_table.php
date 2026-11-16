<?php

use yii\db\Migration;

/**
 * Cria a tabela `country` e insere alguns registos.
 * Aplicar:  php yii migrate
 * Reverter: php yii migrate/down
 */
class m260924_000000_create_country_table extends Migration
{
    public function safeUp()
    {
        $this->createTable('country', [
            'code' => $this->char(2)->notNull(),
            'name' => $this->string(52)->notNull(),
            'population' => $this->integer()->notNull(),
            'PRIMARY KEY(code)',
        ]);

        $this->batchInsert('country', ['code', 'name', 'population'], [
            ['AU', 'Australia', 24016400],
            ['BR', 'Brazil', 205722000],
            ['CA', 'Canada', 35985751],
            ['CN', 'China', 1375210000],
            ['PT', 'Portugal', 10309573],
            ['US', 'United States', 322976000],
        ]);
    }

    public function safeDown()
    {
        $this->dropTable('country');
    }
}