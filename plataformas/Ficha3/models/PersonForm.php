<?php

namespace app\models;

use yii\base\Model;

/**
 * Modelo do formulário de registo de pessoa.
 *
 * Estende yii\base\Model (NÃO yii\db\ActiveRecord) - não está ligado a
 * nenhuma tabela da BD, existe só em memória durante o pedido HTTP.
 * Serve para: 1) definir os campos do formulário; 2) definir as regras
 * de validação (rules()); 3) ser validado com validate().
 *
 * Não representa um utilizador da aplicação (não faz login).
 */
class PersonForm extends Model
{
    public $name;
    public $email;
    public $birthDate;

    /**
     * Regras de validação, usadas por validate() e também pela
     * ActiveForm para validação no browser (JS).
     */
    public function rules()
    {
        return [
            [['name', 'email'], 'required'],
            ['name', 'string', 'min' => 2, 'max' => 128],
            ['email', 'email'],
            ['birthDate', 'date', 'format' => 'php:Y-m-d', 'skipOnEmpty' => true],
        ];
    }

    /**
     * Rótulos em português usados nas labels e mensagens de erro.
     */
    public function attributeLabels()
    {
        return [
            'name' => 'Nome',
            'email' => 'Email',
            'birthDate' => 'Data de nascimento',
        ];
    }
}