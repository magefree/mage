package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.UntapSourceDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BendersWaterskin extends CardImpl {

    public BendersWaterskin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Untap this artifact during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(new UntapSourceDuringEachOtherPlayersUntapStepEffect()));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private BendersWaterskin(final BendersWaterskin card) {
        super(card);
    }

    @Override
    public BendersWaterskin copy() {
        return new BendersWaterskin(this);
    }
}
