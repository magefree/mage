package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class Spellbook extends CardImpl {

    public Spellbook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        this.subtype.add(SubType.BOOK);

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield, HandSizeModification.SET
        )));
    }

    private Spellbook(final Spellbook card) {
        super(card);
    }

    @Override
    public Spellbook copy() {
        return new Spellbook(this);
    }
}
