<?php

/** @var yii\web\View $this */
/** @var app\models\PersonForm $model */
/** @var bool $submitted */

use yii\bootstrap5\ActiveForm;
use yii\bootstrap5\Html;

$this->title = 'Registo de Pessoa';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="site-person">
    <h1><?= Html::encode($this->title) ?></h1>

    <?php if ($submitted): ?>

        <!-- Depois de um POST válido: usamos a API Html para construir
             manualmente os elementos HTML a partir de PHP -->
        <div class="alert alert-success">Pessoa registada com sucesso!</div>

        <?= Html::tag('ul', implode('', [
        Html::tag('li', Html::tag('strong', 'Nome: ') . Html::encode($model->name)),
        Html::tag('li', Html::tag('strong', 'Email: ') . Html::encode($model->email)),
        Html::tag('li', Html::tag('strong', 'Data de nascimento: ') . Html::encode($model->birthDate ?: '-')),
    ])) ?>

        <p><?= Html::a('Registar outra pessoa', ['site/person'], ['class' => 'btn btn-primary']) ?></p>

    <?php else: ?>

        <p>Preencha o formulário para registar uma nova pessoa.</p>

        <div class="row">
            <div class="col-lg-6">
                <?php
                // ActiveForm::begin()/end() geram <form>...</form>,
                // tratando CSRF e a ligação ao $model automaticamente
                $form = ActiveForm::begin(['id' => 'person-form']);
                ?>
                <?= $form->field($model, 'name')->textInput(['autofocus' => true]) ?>
                <?= $form->field($model, 'email') ?>
                <?= $form->field($model, 'birthDate')->input('date') ?>

                <div class="form-group">
                    <?= Html::submitButton('Registar', ['class' => 'btn btn-primary']) ?>
                </div>
                <?php ActiveForm::end(); ?>
            </div>
        </div>

    <?php endif; ?>

    <p><?= Html::a('&laquo; Voltar ao início', ['site/index']) ?></p>
</div>