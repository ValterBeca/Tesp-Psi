<?php

namespace app\controllers;

use yii\web\Controller;
use yii\data\ActiveDataProvider;
use app\models\Country;

class CountryController extends Controller
{
    /**
     * ActionHandler que lista os países guardados na BD.
     * ActiveDataProvider usa Country::find() (= SELECT * FROM country)
     * e trata da paginação.
     *
     * @return string
     */
    public function actionIndex()
    {
        $dataProvider = new ActiveDataProvider([
            'query' => Country::find(),
            'pagination' => ['pageSize' => 5],
        ]);

        return $this->render('index', ['dataProvider' => $dataProvider]);
    }
}