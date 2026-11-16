<?php

use yii\db\Migration;

class m261105_215250_add_deleted_at_column extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        //coluna para o soft delete:
        //  NULL   -> registo ativo
        //  numero -> registo apagado (timestamp do momento em que foi apagado)
        //->null() deixa a coluna aceitar NULL e ->defaultValue(null) faz com que
        //os registos novos (e os que ja existem) fiquem ativos
        $this->addColumn('{{%genre}}', 'deleted_at', $this->integer()->null()->defaultValue(null));
        $this->addColumn('{{%book}}', 'deleted_at', $this->integer()->null()->defaultValue(null));
        $this->addColumn('{{%chapter}}', 'deleted_at', $this->integer()->null()->defaultValue(null));
    }

    public function safeDown()
    {
        //desfazer: retirar a coluna das 3 tabelas (ordem inversa)
        $this->dropColumn('{{%chapter}}', 'deleted_at');
        $this->dropColumn('{{%book}}', 'deleted_at');
        $this->dropColumn('{{%genre}}', 'deleted_at');
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m261105_215250_add_deleted_at_column cannot be reverted.\n";

        return false;
    }
    */
}
