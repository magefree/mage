package mage.cards.l;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazotepBehemoth extends CardImpl {

    public LazotepBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HIPPO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
    }

    private LazotepBehemoth(final LazotepBehemoth card) {
        super(card);
    }

    @Override
    public LazotepBehemoth copy() {
        return new LazotepBehemoth(this);
    }
}
