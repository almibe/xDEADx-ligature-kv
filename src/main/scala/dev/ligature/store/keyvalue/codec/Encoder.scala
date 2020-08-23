package dev.ligature.store.keyvalue.codec

import dev.ligature.store.keyvalue.Prefixes
import dev.ligature._
import scodec.bits.{BitVector, ByteVector}
import scodec.codecs.{byte, long, utf8}
import scodec.{Attempt, Codec, DecodeResult, SizeBound}

object Encoder {


  def encodeSubject(entity: Element): ByteVector = ???

  case class ElementEncoding(`type`: Byte, id: Long)

  def encodeObject(obj: Element): ByteVector = ???

  case class NamedEntitiesToIdKey(prefix: Byte, collectionId: Long, namedElement: String)

  def encodeNamedEntitiesToIdKey(collectionId: Long, entity: NamedElement): ByteVector = {
    Codec.encode(NamedEntitiesToIdKey(Prefixes.NamedElementsToId, collectionId, entity.identifier)).require.bytes
  }

  def encodeNamedEntitiesToIdValue(nextId: Long): ByteVector = {
    long(64).encode(nextId).require.bytes
  }

  case class IdToNamedEntitiesKey(prefix: Byte, collectionId: Long, entity: Long)

  def encodeIdToNamedEntitiesKey(collectionId: Long, entity: Long): ByteVector = {
    Codec.encode(IdToNamedEntitiesKey(Prefixes.IdToNamedElements, collectionId, entity)).require.bytes
  }

  def encodeIdToNamedEntitiesValue(entity: NamedElement): ByteVector = {
    utf8.encode(entity.identifier).require.bytes
  }

  case class PredicatesToIdKey(prefix: Byte, collectionId: Long, predicate: String)

  def encodePredicatesToIdKey(collectionId: Long, predicate: NamedElement): ByteVector = {
    Codec.encode(PredicatesToIdKey(Prefixes.NamedElementsToId, collectionId, predicate.identifier)).require.bytes
  }

  def encodePredicatesToIdValue(nextId: Long): ByteVector = {
    long(64).encode(nextId).require.bytes
  }

  case class IdToPredicatesKey(prefix: Byte, collectionId: Long, predicateId: Long)

  def encodeIdToPredicatesKey(collectionId: Long, predicateId: Long): ByteVector = {
    Codec.encode(IdToPredicatesKey(Prefixes.IdToNamedElements, collectionId, predicateId)).require.bytes
  }

  def encodeIdToPredicatesValue(predicate: NamedElement): ByteVector = {
    utf8.encode(predicate.identifier).require.bytes
  }

  case class StringToIdKey(prefix: Byte, collectionId: Long, stringLiteral: String)

  def encodeStringToIdKey(collectionId: Long, stringLiteral: StringLiteral): ByteVector = {
    Codec.encode(StringToIdKey(Prefixes.StringToId, collectionId, stringLiteral.value)).require.bytes
  }

  def encodeStringToIdValue(nextId: Long): ByteVector = {
    long(64).encode(nextId).require.bytes
  }

  case class IdToStringKey(prefix: Byte, collectionId: Long, stringLiteralId: Long)

  def encodeIdToStringKey(collectionId: Long, stringLiteralId: Long): ByteVector = {
    Codec.encode(IdToStringKey(Prefixes.IdToNamedElements, collectionId, stringLiteralId)).require.bytes
  }

  def encodeIdToStringValue(stringLiteral: StringLiteral): ByteVector = {
    utf8.encode(stringLiteral.value).require.bytes
  }

  def encodeStatement(collectionId: Long, statement: Statement): Seq[ByteVector] = ???

  case class SPO(prefix: Byte,
                 collectionId: Long,
                 subject: Option[ElementEncoding],
                 predicateId: Option[Long],
                 `object`: Option[ElementEncoding])

  def encodeSPOPrefix(collectionId: Long,
                      subject: Option[ElementEncoding],
                      predicate: Option[Long],
                      `object`: Option[ElementEncoding]): ByteVector = {
    Codec.encode(SPO(Prefixes.SPOC, collectionId, subject, predicate, `object`)).require.bytes
  }

  case class SOP(prefix: Byte,
                 collectionId: Long,
                 subject: Option[ElementEncoding],
                 `object`: Option[ElementEncoding],
                 predicateId: Option[Long])

  def encodeSOPPrefix(collectionId: Long,
                      subject: Option[ElementEncoding],
                      predicate: Option[Long],
                      `object`: Option[ElementEncoding]): ByteVector = {
    Codec.encode(SOP(Prefixes.SOPC, collectionId, subject, `object`, predicate)).require.bytes
  }

  case class PSO(prefix: Byte,
                 collectionId: Long,
                 predicateId: Option[Long],
                 subject: Option[ElementEncoding],
                 `object`: Option[ElementEncoding])

  def encodePSOPrefix(collectionId: Long,
                      subject: Option[ElementEncoding],
                      predicate: Option[Long],
                      `object`: Option[ElementEncoding]): ByteVector = {
    Codec.encode(PSO(Prefixes.PSOC, collectionId, predicate, subject, `object`)).require.bytes
  }

  case class POS(prefix: Byte,
                 collectionId: Long,
                 predicateId: Option[Long],
                 `object`: Option[ElementEncoding],
                 subject: Option[ElementEncoding])

  def encodePOSPrefix(collectionId: Long,
                      subject: Option[ElementEncoding],
                      predicate: Option[Long],
                      `object`: Option[ElementEncoding]): ByteVector = {
    Codec.encode(POS(Prefixes.POSC, collectionId, predicate, `object`, subject)).require.bytes
  }

  case class OSP(prefix: Byte,
                 collectionId: Long,
                 `object`: Option[ElementEncoding],
                 subject: Option[ElementEncoding],
                 predicateId: Option[Long])

  def encodeOSPPrefix(collectionId: Long,
                      subject: Option[ElementEncoding],
                      predicate: Option[Long],
                      `object`: Option[ElementEncoding]): ByteVector = {
    Codec.encode(OSP(Prefixes.OSPC, collectionId, `object`, subject, predicate)).require.bytes
  }

  case class OPS(prefix: Byte,
                 collectionId: Long,
                 `object`: Option[ElementEncoding],
                 predicateId: Option[Long],
                 subject: Option[ElementEncoding])

  def encodeOPSPrefix(collectionId: Long,
                      subject: Option[ElementEncoding],
                      predicate: Option[Long],
                      `object`: Option[ElementEncoding]): ByteVector = {
    Codec.encode(OPS(Prefixes.OPSC, collectionId, `object`, predicate, subject)).require.bytes
  }

  //  def encodeSOPStartStop(collectionId: Long,
  //                         subject: Option[ElementEncoding],
  //                         predicate: Option[Long],
  //                         literalRange: Range[_]): (ByteVector, ByteVector) = {
  //    ???
  //  }
  //
  //  def encodePOSStartStop(collectionId: Long,
  //                         subject: Option[ElementEncoding],
  //                         predicate: Option[Long],
  //                         literalRange: Range[_]): (ByteVector, ByteVector) = {
  //    ???
  //  }
  //
  //  def encodeOSPStartStop(collectionId: Long,
  //                         subject: Option[ElementEncoding],
  //                         predicate: Option[Long],
  //                         literalRange: Range[_]): (ByteVector, ByteVector) = {
  //    ???
  //  }
  //
  //  def encodeOPSStartStop(collectionId: Long,
  //                         subject: Option[ElementEncoding],
  //                         predicate: Option[Long],
  //                         literalRange: Range[_]): (ByteVector, ByteVector) = {
  //    ???
  //  }

  case class SPOC(prefix: Byte,
                  collectionId: Long,
                  subject: ElementEncoding,
                  predicateId: Long,
                  `object`: ElementEncoding,
                  context: Long)

  def encodeSPOC(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(SPOC(Prefixes.SPOC, collectionId, subject, predicateId, obj, context.identifier)).require.bytes
  }

  case class SOPC(prefix: Byte,
                  collectionId: Long,
                  subject: ElementEncoding,
                  `object`: ElementEncoding,
                  predicateId: Long,
                  context: Long)

  def encodeSOPC(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(SOPC(Prefixes.SOPC, collectionId, subject, obj, predicateId, context.identifier)).require.bytes
  }

  case class PSOC(prefix: Byte,
                  collectionId: Long,
                  predicateId: Long,
                  subject: ElementEncoding,
                  `object`: ElementEncoding,
                  context: Long)

  def encodePSOC(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(PSOC(Prefixes.PSOC, collectionId, predicateId, subject, obj, context.identifier)).require.bytes
  }

  case class POSC(prefix: Byte,
                  collectionId: Long,
                  predicateId: Long,
                  `object`: ElementEncoding,
                  subject: ElementEncoding,
                  context: Long)

  def encodePOSC(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(POSC(Prefixes.POSC, collectionId, predicateId, obj, subject, context.identifier)).require.bytes
  }

  case class OSPC(prefix: Byte,
                  collectionId: Long,
                  `object`: ElementEncoding,
                  subject: ElementEncoding,
                  predicateId: Long,
                  context: Long)

  def encodeOSPC(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(OSPC(Prefixes.OSPC, collectionId, obj, subject, predicateId, context.identifier)).require.bytes
  }

  case class OPSC(prefix: Byte,
                  collectionId: Long,
                  `object`: ElementEncoding,
                  predicateId: Long,
                  subject: ElementEncoding,
                  context: Long)

  def encodeOPSC(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(OPSC(Prefixes.OPSC, collectionId, obj, predicateId, subject, context.identifier)).require.bytes
  }

  case class CSPO(prefix: Byte,
                  collectionId: Long,
                  context: Long,
                  subject: ElementEncoding,
                  predicateId: Long,
                  `object`: ElementEncoding)

  def encodeCSPO(collectionId: Long, subject: ElementEncoding, predicateId: Long,
                 obj: ElementEncoding, context: AnonymousElement): ByteVector = {
    Codec.encode(CSPO(Prefixes.CSPO, collectionId, context.identifier, subject, predicateId, obj)).require.bytes
  }

  private val byteLong = byte ~~ long(64)

  def encodeSPOCScanStart(collectionId: Long): ByteVector = {
    byteLong.encode(Prefixes.SPOC, collectionId).require.bytes
  }

  private case class CollectionCounterKey(prefix: Byte, collectionId: Long)

  def encodeCollectionCounterKey(collectionId: Long): ByteVector = {
    Codec.encode(CollectionCounterKey(Prefixes.CollectionCounter, collectionId)).require.bytes
  }

  def encodeCollectionCounterValue(value: Long): ByteVector = {
    Codec.encode(value).require.bytes
  }

  private case class AnonymousElementKey(prefix: Byte, collectionId: Long, anonymousId: Long)

  def encodeAnonymousElementKey(collectionId: Long, anonymousId: Long): ByteVector = {
    Codec.encode(AnonymousElementKey(Prefixes.AnonymousElements, collectionId, anonymousId)).require.bytes
  }
}
