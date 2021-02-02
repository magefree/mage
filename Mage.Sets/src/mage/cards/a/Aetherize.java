package mage.cards.a;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Aetherize extends CardImpl {

    public Aetherize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Return all attacking creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("Return all attacking creatures to their owner's hand."));
    }

    private Aetherize(final Aetherize card) {
        super(card);
    }

    @Override
    public Aetherize copy() {
        return new Aetherize(this);
    }
}
