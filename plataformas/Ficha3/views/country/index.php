<?php

/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */

use yii\grid\GridView;
use yii\bootstrap5\LinkPager;
use yii\bootstrap5\Html;

$this->title = 'Países';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="country-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => ['code', 'name', 'population'],
        // Barra de paginação "bonita": usa o LinkPager do Bootstrap 5
        // (arredondado, com estado "ativo" a azul) em vez do LinkPager
        // genérico do Yii, e centra-a por baixo da tabela.
        'pager' => [
            'class' => LinkPager::class,
            'listOptions' => ['class' => ['pagination', 'justify-content-center', 'mt-3']],
        ],
    ]) ?>
</div>