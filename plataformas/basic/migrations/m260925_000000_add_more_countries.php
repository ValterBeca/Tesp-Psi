<?php

use yii\db\Migration;

/**
 * Acrescenta mais países à tabela `country` (criada em
 * m260924_000000_create_country_table).
 */
class m260925_000000_add_more_countries extends Migration
{
    public function safeUp()
    {
        $this->batchInsert('country', ['code', 'name', 'population'], [
            ['DE', 'Germany', 81802257],
            ['FR', 'France', 64513242],
            ['IN', 'India', 1285400000],
            ['RU', 'Russia', 146519759],
            ['JP', 'Japan', 126440000],
            ['GB', 'United Kingdom', 66435600],
        ]);
    }

    public function safeDown()
    {
        $this->delete('country', ['code' => ['DE', 'FR', 'IN', 'RU', 'JP', 'GB']]);
    }
}