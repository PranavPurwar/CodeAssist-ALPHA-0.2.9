package com.tyron.builder.compiler.apk;

import android.util.Log;
import com.tyron.builder.compiler.ApkSigner;
import com.tyron.builder.compiler.BuildType;
import com.tyron.builder.compiler.Task;
import com.tyron.builder.exception.CompilationFailedException;
import com.tyron.builder.log.ILogger;
import com.tyron.builder.project.Project;
import com.tyron.builder.project.api.AndroidModule;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class SignTask extends Task<AndroidModule> {

  private File mInputApk;
  private File mOutputApk;

  public SignTask(Project project, AndroidModule module, ILogger logger) {
    super(project, module, logger);
  }

  @Override
  public String getName() {
    return "sign";
  }

  @Override
  public void prepare(BuildType type) throws IOException {
    mInputApk = new File(getModule().getBuildDirectory(), "bin/aligned.apk");
    mOutputApk = new File(getModule().getBuildDirectory(), "bin/signed.apk");

    if (!mInputApk.exists()) {
      mInputApk = new File(getModule().getBuildDirectory(), "bin/generated.apk");
    }

    if (!mInputApk.exists()) {
      throw new IOException("Unable to find generated apk file.");
    }

    Log.d(getName().toString(), "Signing APK.");
  }

  @Override
  public void run() throws IOException, CompilationFailedException {
    ApkSigner signer =
        new ApkSigner(
            mInputApk.getAbsolutePath(), mOutputApk.getAbsolutePath(), ApkSigner.Mode.TEST);

    try {
      signer.sign();
    } catch (Exception e) {
      throw new CompilationFailedException(e);
    }

    FileUtils.forceDelete(mInputApk);
  }
}
