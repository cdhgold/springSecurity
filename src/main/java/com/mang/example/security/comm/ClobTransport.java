/*=========================================================
* Copyright(c) 2020 남성해운
*@FileName :  ClobTransport.java
*@Create Data :2020. 10. 5. 
*@History :
* 1.0, 2020.10.05최초작성
* ehddh,   2020. 10. 5. 
===========================================================*/
package com.mang.example.security.comm;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * 클래스설명: Clob 변환
 * <pre>
 * 프로그램관리
 * </pre>
 * @author
 */

public class ClobTransport
  implements Clob
{
  private Object source;

  private ClobTransport(Object source)
  {
    this.source = source;
  }

  public Object source() {
    return this.source;
  }

  public long length() throws SQLException {
    return clob().length();
  }

  public String getSubString(long pos, int length) throws SQLException {
    return clob().getSubString(pos, length);
  }

  public Reader getCharacterStream() throws SQLException {
    return clob().getCharacterStream();
  }

  public InputStream getAsciiStream() throws SQLException {
    return clob().getAsciiStream();
  }

  public long position(String searchstr, long start) throws SQLException {
    return clob().position(searchstr, start);
  }

  public long position(Clob searchstr, long start) throws SQLException {
    return clob().position(searchstr, start);
  }

  public int setString(long pos, String str) throws SQLException {
    return clob().setString(pos, str);
  }

  public int setString(long pos, String str, int offset, int len) throws SQLException {
    return clob().setString(pos, str, offset, len);
  }

  public OutputStream setAsciiStream(long pos) throws SQLException {
    return clob().setAsciiStream(pos);
  }

  public Writer setCharacterStream(long pos) throws SQLException {
    return clob().setCharacterStream(pos);
  }

  public void truncate(long len) throws SQLException {
    clob().truncate(len);
  }

  public void free() throws SQLException {
    clob().free();
  }

  public Reader getCharacterStream(long pos, long length) throws SQLException {
    return clob().getCharacterStream(pos, length);
  }

  private Clob clob() {
    if (!(this.source instanceof Clob)) {
      throw new UnsupportedOperationException();
    }
    return (Clob)this.source;
  }

  public static Clob wrap(Object source) {
    if (source instanceof ClobTransport) {
      return (Clob)source;
    }
    return new ClobTransport(source);
  }
}