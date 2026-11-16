<?php

namespace frontend\controllers;

use frontend\models\Chapter;
use frontend\models\ChapterSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use frontend\models\Book;
use yii\data\ActiveDataProvider;

/**
 * ChapterController implements the CRUD actions for Chapter model.
 */
class ChapterController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all Chapter models.
     *
     * @return string
     */
    public function actionIndex($bookid)
    {
        //o livro (mestre) a que os capitulos pertencem
        $book = Book::findOne($bookid);

        //so os capitulos desse livro (relacao hasMany, usada como query)
        $dataProvider = new ActiveDataProvider([
            'query' => $book->getChapters(),
        ]);

        return $this->render('index', [
            'dataProvider' => $dataProvider,
            'book' => $book
        ]);
    }

    /**
     * Displays a single Chapter model.
     * @param int $id ID
     * @return string
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
    }

    /**
     * Creates a new Chapter model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate($bookid)
    {
        $model = new Chapter();
        //o capitulo fica ligado ao livro recebido no URL
        $model->book_id = $bookid;

        if ($this->request->isPost) {
            if ($model->load($this->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Chapter model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->id]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing Chapter model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return \yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        //guardar o capitulo antes de apagar, para saber de que livro e
        $model = $this->findModel($id);

        //soft delete: o capitulo fica na BD com deleted_at preenchido
        //e deixa de aparecer na lista (o find() do trait esconde-o)
        $model->softDelete();

        //o index dos capitulos precisa do id do livro
        return $this->redirect(['index', 'bookid' => $model->book_id]);
    }

    /**
     * Finds the Chapter model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Chapter the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Chapter::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
