<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var frontend\models\Chapter $model */

$this->title = 'Create Chapter';
$this->params['breadcrumbs'][] = ['label' => 'Chapters', 'url' => ['index', 'bookid' => $model->book_id]];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="chapter-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
