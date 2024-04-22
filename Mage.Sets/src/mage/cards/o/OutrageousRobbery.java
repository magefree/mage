package mage.cards.o;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author DominionSpy
 */
public final class OutrageousRobbery extends CardImpl {

    public OutrageousRobbery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");

        // Target opponent exiles the top X cards of their library face down. You may look at and play those cards for as long as they remain exiled. If you cast a spell this way, you may spend mana as though it were mana of any type to cast it.
        this.getSpellAbility().addEffect(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(
                        false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE, ManacostVariableValue.REGULAR
                ).setText("Target opponent exiles the top X cards of their library face down. "
                        + "You may look at and play those cards for as long as they remain exiled. "
                        + "If you cast a spell this way, you may spend mana as though it were mana of any type to cast it.")
        );
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private OutrageousRobbery(final OutrageousRobbery card) {
        super(card);
    }

    @Override
    public OutrageousRobbery copy() {
        return new OutrageousRobbery(this);
    }
}