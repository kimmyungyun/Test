#include <jni.h>
#include "cv.h"
#include "com_example_user_myapplication_MainActivity.h"
#include <android/log.h>
#include <opencv2/opencv.hpp>
#include <vector>

#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace cv;
using namespace std;
extern "C" {

JNIEXPORT void JNICALL Java_com_example_user_myapplication_MainActivity_cvGray
        (JNIEnv *env, jobject instance, jlong matAddrInput, jlong matAddrResult){
    Mat &matInput = *(Mat *)matAddrInput;
    Mat &matResult = *(Mat *)matAddrResult;

    cvtColor(matInput, matResult, CV_RGBA2GRAY);
    }
JNIEXPORT jint JNICALL Java_com_example_user_myapplication_MainActivity_detectFace
        (JNIEnv *env, jobject, jstring jFileName, jlong addrRgba, jlong addrRetVal){
            const char* jnamestr = env->GetStringUTFChars(jFileName, NULL);
            string stdFileName(jnamestr);

            Mat& mRgba = *(Mat*)addrRgba;
            Mat& retValMat = *(Mat*)addrRetVal;
            Mat gray;

            vector<Rect> faces;

            jint retVal;
            int faceFound = 0;

            mRgba.copyTo(retValMat);

            cvtColor(mRgba, gray, CV_RGB2GRAY);

            CascadeClassifier face_cascade;
            face_cascade.load(stdFileName);

            face_cascade.detectMultiScale(gray, faces, 2, 1,
                                          CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_SCALE_IMAGE,
                                          Size(30,30),Size(900,900));
         LOGD("CascadeDetectorAdapter::Detect: width = %d, height = %d", mRgba.cols, mRgba.rows );
            if(faces.size() > 0)
            {
                int index;
                Rect face;
                for(index = 0; index<faces.size(); index++){
                    face = faces[index];
                    rectangle(retValMat, face, Scalar(255,0,0),3);
                    LOGD("CascadeDetectorAdapter::Detect: center x = %d, center y = %d, width = %d, height = %d", face.x, face.y, face.width, face.height);
                    retValMat = retValMat(face);
                    LOGD("CascadeDetectorAdapter::Detect:  width = %d, height = %d", retValMat.cols, retValMat.rows);

                }
                faceFound = 1;

            }
            else{
                faceFound = 0;
            }

    retVal = (jint)faceFound;

    return retVal;
    }
JNIEXPORT jdouble JNICALL Java_com_example_user_myapplication_MainActivity_Similarity
        (JNIEnv *env, jobject, jlong img1, jlong img2){
            Mat& input = *(Mat*)img1;
            Mat& imput2 = *(Mat*)img2;

            Mat gray1;
            Mat gray2;

            cvtColor(input, gray1, CV_RGB2GRAY);
            cvtColor(imput2, gray2, CV_RGB2GRAY);
    }
}