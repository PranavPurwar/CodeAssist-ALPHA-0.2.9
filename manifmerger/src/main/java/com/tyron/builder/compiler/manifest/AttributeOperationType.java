package com.tyron.builder.compiler.manifest;

/**
 * Defines attributes operations as it can be provided by users through attributes on the target xml
 * element.
 *
 * <p>For instance:
 *
 * <pre>{@code
 * <uses-permission
 *     android:name="android.permission.CAMERA"
 *     android:maxSdkVersion=25
 *     tools:replace="android:maxSdkVersion"/>
 *
 * }</pre>
 *
 * <p>The operation type is provided as part of the tools attribute name itself, so you can find
 * tools:remove, tools:replace, tools:strict. The value of that attribute is a comma separated list
 * of attribute names on which the operation applies.
 *
 * <p>For instance:
 *
 * <pre>{@code
 * <permission
 *     android:name="android.permission.CAMERA"
 *     android:icon="@Res/foo"
 *     android:protectionLevel="dangerous"
 *     tools:replace="android:maxSdkVersion, protectionLevel"/>
 *
 * }</pre>
 *
 * will replace maxSdkVersion and protectionLevel attributes values when merging lower level xml
 * elements.
 */
enum AttributeOperationType {

  /** Removes the attributes from all further merging activities. */
  REMOVE,

  /**
   * Replace the attributes values with the provided one. (Will generate a merging error if no new
   * value is provided).
   */
  REPLACE,

  /** The attributes should not be specified by any lower priority xml elements. */
  STRICT,

  IGNORE_WARNING
}
