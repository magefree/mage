package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class GeminiEngineTwinToken extends TokenImpl {

    public GeminiEngineTwinToken() {
        this(0, 0);
    }

    public GeminiEngineTwinToken(int power, int toughness) {
        super("Twin", "colorless Construct artifact creature token named Twin that's attacking. Its power is equal to Gemini Engine's power and its toughness is equal to Gemini Engine's toughness.");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

    protected GeminiEngineTwinToken(final GeminiEngineTwinToken token) {
        super(token);
    }

    public GeminiEngineTwinToken copy() {
        return new GeminiEngineTwinToken(this);
    }
}
