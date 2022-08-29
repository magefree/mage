package mage.cards.d;

import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Doublecast extends CardImpl {

    public Doublecast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}");

        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));
    }

    private Doublecast(final Doublecast card) {
        super(card);
    }

    @Override
    public Doublecast copy() {
        return new Doublecast(this);
    }
}
