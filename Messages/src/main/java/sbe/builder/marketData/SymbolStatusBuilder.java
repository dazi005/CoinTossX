package sbe.builder.marketData;

import sbe.msg.MessageHeaderEncoder;
import sbe.msg.marketData.*;
import uk.co.real_logic.agrona.DirectBuffer;
import uk.co.real_logic.agrona.concurrent.UnsafeBuffer;

import java.nio.ByteBuffer;

/**
 * Created by dharmeshsing on 12/08/15.
 */
public class SymbolStatusBuilder {
    private int bufferIndex;
    private SymbolStatusEncoder symbolStatus;
    private MessageHeaderEncoder messageHeader;
    private UnsafeBuffer encodeBuffer;

    private MessageTypeEnum messageType;
    private long nanosecond;
    private int instrumentId;
    private TradingSessionEnum tradingSession;
    private HaltReasonEnum haltReason;
    private SessionChangedReasonEnum sessionChangedReason;
    private int compID;

    public static int BUFFER_SIZE = 27;

    public SymbolStatusBuilder(){
        symbolStatus = new SymbolStatusEncoder();
        messageHeader = new MessageHeaderEncoder();
        encodeBuffer = new UnsafeBuffer(ByteBuffer.allocateDirect(BUFFER_SIZE));
    }

    public SymbolStatusBuilder compID(int value){
        this.compID = value;
        return this;
    }

    public SymbolStatusBuilder messageType(MessageTypeEnum value){
        this.messageType = value;
        return this;
    }

    public SymbolStatusBuilder nanosecond(long value){
        this.nanosecond = value;
        return this;
    }

    public SymbolStatusBuilder instrumentId(int value){
        this.instrumentId = value;
        return this;
    }

    public SymbolStatusBuilder tradingSession(TradingSessionEnum value){
        this.tradingSession = value;
        return this;
    }

    public SymbolStatusBuilder haltReason(HaltReasonEnum value){
        this.haltReason = value;
        return this;
    }

    public SymbolStatusBuilder sessionChangedReason(SessionChangedReasonEnum value){
        this.sessionChangedReason = value;
        return this;
    }

    public DirectBuffer build(){
        bufferIndex = 0;
        messageHeader.wrap(encodeBuffer, bufferIndex)
                .blockLength(symbolStatus.sbeBlockLength())
                .templateId(symbolStatus.sbeTemplateId())
                .schemaId(symbolStatus.sbeSchemaId())
                .version(symbolStatus.sbeSchemaVersion())
                .compID(compID);

        bufferIndex += messageHeader.encodedLength();
        symbolStatus.wrap(encodeBuffer, bufferIndex)
                .instrumentId(instrumentId)
                .messageType(messageType)
                .nanosecond(nanosecond)
                .tradingSession(tradingSession)
                .haltReason(haltReason)
                .sessionChangedReason(sessionChangedReason);


        return encodeBuffer;
    }
}
