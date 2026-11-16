<?php
use yii\helpers\Html;
use yii\grid\GridView;

$this->title = 'Capítulos de ' . $book->name;
?>
<div class="chapter-index">

    <h1><?= Html::encode($this->title) ?></h1>
    <p><small>ISBN: <?= Html::encode($book->isbn) ?></small></p>

    <p>
        <?= Html::a('Create Chapter', ['create', 'bookid' => $book->id], ['class' => 'btn btn-success']) ?>
    </p>

    <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'columns' => [
                    ['class' => 'yii\grid\SerialColumn'],
                    'id',
                    'name',
                    ['class' => 'yii\grid\ActionColumn'],
            ],
    ]); ?>

</div>
