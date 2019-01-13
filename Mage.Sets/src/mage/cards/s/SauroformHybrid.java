package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SauroformHybrid extends CardImpl {

    public SauroformHybrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{G}{G}: Adapt 4.
        this.addAbility(new AdaptAbility(4, "{4}{G}{G}"));
    }

    private SauroformHybrid(final SauroformHybrid card) {
        super(card);
    }

    @Override
    public SauroformHybrid copy() {
        return new SauroformHybrid(this);
    }
}
