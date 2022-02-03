package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class LikenessOfTheSeeker extends CardImpl {

    public LikenessOfTheSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Likeness of the Seeker becomes blocked, untap up to three lands you control.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new UntapLandsEffect(3, true, true), false));
    }

    private LikenessOfTheSeeker(final LikenessOfTheSeeker card) {
        super(card);
    }

    @Override
    public LikenessOfTheSeeker copy() {
        return new LikenessOfTheSeeker(this);
    }
}
