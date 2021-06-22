package mage.cards.s;

import java.util.UUID;

import mage.abilities.condition.common.ForetoldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.AngelWarriorVigilanceToken;

/**
 *
 * @author weirddan455
 */
public final class StarnheimUnleashed extends CardImpl {

    public StarnheimUnleashed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Create a 4/4 white Angel Warrior creature token with flying and vigilance. If this spell was foretold, create X of those tokens instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new AngelWarriorVigilanceToken(), ManacostVariableValue.REGULAR),
                new CreateTokenEffect(new AngelWarriorVigilanceToken()),
                ForetoldCondition.instance,
                "Create a 4/4 white Angel Warrior creature token with flying and vigilance. If this spell was foretold, create X of those tokens instead"
        ));

        // Foretell {X}{X}{W}
        this.addAbility(new ForetellAbility(this, "{X}{X}{W}"));
    }

    private StarnheimUnleashed(final StarnheimUnleashed card) {
        super(card);
    }

    @Override
    public StarnheimUnleashed copy() {
        return new StarnheimUnleashed(this);
    }
}
