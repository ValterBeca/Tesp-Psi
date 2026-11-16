<?php

/** @var yii\web\View $this */
/** @var string $message */

use yii\bootstrap5\Html;

// Título da página / breadcrumb
$this->title = 'Hello';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="site-say">
    <?php
    // Html::encode() escapa HTML especial (<, >, &...) - protege contra
    // XSS, já que $message vem de um parâmetro controlado pelo utilizador
    echo Html::encode($message);
    ?>
</div>