<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "prato".
 *
 * @property int $id
 * @property string|null $nome
 * @property string|null $descricao
 * @property int|null $preco
 * @property int|null $disponivel
 * @property string|null $data_criacao
 */
class Prato extends \yii\db\ActiveRecord
{


    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'prato';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['nome', 'descricao', 'preco', 'disponivel', 'data_criacao'], 'default', 'value' => null],
            [['preco', 'disponivel'], 'integer'],
            [['data_criacao'], 'safe'],
            [['nome'], 'string', 'max' => 255],
            [['descricao'], 'string', 'max' => 100],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nome' => 'Nome',
            'descricao' => 'Descricao',
            'preco' => 'Preco',
            'disponivel' => 'Disponivel',
            'data_criacao' => 'Data Criacao',
        ];
    }

}
