<?php

namespace app\models;

use yii\db\ActiveRecord;

/**
 * RESPOSTA à pergunta 5.d.i "como se torna um modelo num modelo
 * ActiveRecord":
 *   1) a classe estende yii\db\ActiveRecord (em vez de yii\base\Model,
 *      como o PersonForm do exercício 3);
 *   2) implementa-se tableName(), que diz ao Yii a que tabela da BD
 *      este modelo corresponde;
 *   3) não é preciso declarar as colunas (code, name, population) como
 *      propriedades - o ActiveRecord lê-as da BD (via Yii::$app->db) e
 *      expõe-as automaticamente;
 *   4) a partir daqui a classe ganha find()/findOne()/all(), save(),
 *      delete(), etc., "de borla".
 */
class Country extends ActiveRecord
{
    public static function tableName()
    {
        return 'country';
    }
}