package com.meifei.app.data.local.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FlightSessionDao_Impl implements FlightSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FlightSessionEntity> __insertionAdapterOfFlightSessionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public FlightSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlightSessionEntity = new EntityInsertionAdapter<FlightSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `flight_sessions` (`id`,`startAt`,`endAt`,`durationMs`,`locationText`,`note`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FlightSessionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartAt());
        statement.bindLong(3, entity.getEndAt());
        statement.bindLong(4, entity.getDurationMs());
        if (entity.getLocationText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocationText());
        }
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM flight_sessions WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM flight_sessions";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final FlightSessionEntity session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFlightSessionEntity.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FlightSessionEntity>> queryAllDesc() {
    final String _sql = "SELECT * FROM flight_sessions ORDER BY startAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flight_sessions"}, new Callable<List<FlightSessionEntity>>() {
      @Override
      @NonNull
      public List<FlightSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<FlightSessionEntity> _result = new ArrayList<FlightSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlightSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpLocationText;
            if (_cursor.isNull(_cursorIndexOfLocationText)) {
              _tmpLocationText = null;
            } else {
              _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new FlightSessionEntity(_tmpId,_tmpStartAt,_tmpEndAt,_tmpDurationMs,_tmpLocationText,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object queryBetween(final long start, final long end,
      final Continuation<? super List<FlightSessionEntity>> $completion) {
    final String _sql = "SELECT * FROM flight_sessions WHERE startAt BETWEEN ? AND ? ORDER BY startAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlightSessionEntity>>() {
      @Override
      @NonNull
      public List<FlightSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<FlightSessionEntity> _result = new ArrayList<FlightSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlightSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpLocationText;
            if (_cursor.isNull(_cursorIndexOfLocationText)) {
              _tmpLocationText = null;
            } else {
              _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new FlightSessionEntity(_tmpId,_tmpStartAt,_tmpEndAt,_tmpDurationMs,_tmpLocationText,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object queryFrom(final long start,
      final Continuation<? super List<FlightSessionEntity>> $completion) {
    final String _sql = "SELECT * FROM flight_sessions WHERE startAt >= ? ORDER BY startAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlightSessionEntity>>() {
      @Override
      @NonNull
      public List<FlightSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<FlightSessionEntity> _result = new ArrayList<FlightSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlightSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpLocationText;
            if (_cursor.isNull(_cursorIndexOfLocationText)) {
              _tmpLocationText = null;
            } else {
              _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new FlightSessionEntity(_tmpId,_tmpStartAt,_tmpEndAt,_tmpDurationMs,_tmpLocationText,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object queryLast24h(final long start,
      final Continuation<? super List<FlightSessionEntity>> $completion) {
    final String _sql = "SELECT * FROM flight_sessions WHERE startAt >= ? ORDER BY startAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlightSessionEntity>>() {
      @Override
      @NonNull
      public List<FlightSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<FlightSessionEntity> _result = new ArrayList<FlightSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlightSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpLocationText;
            if (_cursor.isNull(_cursorIndexOfLocationText)) {
              _tmpLocationText = null;
            } else {
              _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new FlightSessionEntity(_tmpId,_tmpStartAt,_tmpEndAt,_tmpDurationMs,_tmpLocationText,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object queryLast7d(final long start,
      final Continuation<? super List<FlightSessionEntity>> $completion) {
    final String _sql = "SELECT * FROM flight_sessions WHERE startAt >= ? ORDER BY startAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlightSessionEntity>>() {
      @Override
      @NonNull
      public List<FlightSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<FlightSessionEntity> _result = new ArrayList<FlightSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlightSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpLocationText;
            if (_cursor.isNull(_cursorIndexOfLocationText)) {
              _tmpLocationText = null;
            } else {
              _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new FlightSessionEntity(_tmpId,_tmpStartAt,_tmpEndAt,_tmpDurationMs,_tmpLocationText,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object queryLast30d(final long start,
      final Continuation<? super List<FlightSessionEntity>> $completion) {
    final String _sql = "SELECT * FROM flight_sessions WHERE startAt >= ? ORDER BY startAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlightSessionEntity>>() {
      @Override
      @NonNull
      public List<FlightSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<FlightSessionEntity> _result = new ArrayList<FlightSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlightSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpLocationText;
            if (_cursor.isNull(_cursorIndexOfLocationText)) {
              _tmpLocationText = null;
            } else {
              _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new FlightSessionEntity(_tmpId,_tmpStartAt,_tmpEndAt,_tmpDurationMs,_tmpLocationText,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
