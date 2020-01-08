package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesAllBasicsControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PrismaticOmen extends CardImpl {

    public PrismaticOmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Lands you control are every basic land type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new BecomesAllBasicsControlledEffect()));
    }

    private PrismaticOmen(final PrismaticOmen card) {
        super(card);
    }

    @Override
    public PrismaticOmen copy() {
        return new PrismaticOmen(this);
    }
}
