<?php

namespace backend\modules\api\controllers;
use yii\rest\ActiveController;
use yii\web\BadRequestHttpException;

class PratoController extends ActiveController
{
    public $modelClass = 'common\models\Prato';   //Parte Crud

    public function actionCount()
    {
        $pratoModel = new $this->modelClass;
        $recs = $pratoModel::find()->all();
        return ['count' => count($recs)];
    }

    public function actionNomes()
    {
        $pratosmodel = new $this->modelClass;
        $recs = $pratosmodel::find()->select(['nome'])->all();
        return $recs;
    }

    public function actionPreco($id)
    {
        $pratosmodel = new $this->modelClass;
        //$recs = $pratosmodel::find()->select(['preco'])->where(['id' => $id])->all(); //array
        $recs = $pratosmodel::find()->select(['preco'])->where(['id' => $id])->one(); //objeto json
        return $recs;
    }

    public function actionPrecopornome($nomeprato)
    {
        $pratosmodel = new $this->modelClass;
        $recs = $pratosmodel::find()->select(['preco'])->where(['nome' => $nomeprato])->all(); //array
        return $recs;
    }

    public function actionDelpornome($nomeprato)
    {
        $climodel = new $this->modelClass;
        $recs = $climodel::deleteAll(['nome' => $nomeprato]);
        return $recs;
    }

    public function actionPutprecopornome($nomeprato)
    {
        $novo_preco=\Yii::$app->request->post('preco');
        $climodel = new $this->modelClass;
        $ret = $climodel::findOne(['nome' => $nomeprato]);
        if($ret)
        {
            $ret->preco = $novo_preco;
            $ret->save();
        }
        else
        {
            throw new \yii\web\NotFoundHttpException("Nome de prato não existe");
        }
    }

    public function actionPostpratovazio()
    {
        $pratomodel = new $this->modelClass;
        $pratomodel->id=0; //é autonumber!
        $pratomodel->nome=' ';
        $pratomodel->descricao=' ';
        $pratomodel->preco=0;
        $pratomodel->disponivel=0;
        $pratomodel->save();
        return $pratomodel;
    }

    public function actionPordata($ano, $mes, $dia)
    {
        if (!checkdate((int)$mes, (int)$dia, (int)$ano)) {
            throw new BadRequestHttpException("Data inválida: $ano-$mes-$dia");
        }
        $data = "$ano-$mes-$dia";
        $pratosmodel = new $this->modelClass;
        $recs = $pratosmodel::find()
            ->where(['>=', 'data_criacao', $data])
            ->orderBy(['data_criacao' => SORT_ASC])
            ->all();
        return $recs;
    }
}


