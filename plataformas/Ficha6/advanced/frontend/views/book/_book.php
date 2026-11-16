<?php
use yii\helpers\Html;
?>
<div class="book">

    <?= Html::encode($model->name) ?>
    <p><small><?= Html::encode($model->isbn) ?></small></p>
    <p>
        <?= Html::a('Chapters', ['chapter/index', 'bookid' => $model->id], ['class' => 'btn btn-success']) ?>
    </p>

</div>