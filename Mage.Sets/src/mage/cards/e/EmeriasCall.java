package mage.cards.e;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.AngelWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmeriasCall extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("Non-Angel creatures you control");

    static {
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
    }

    public EmeriasCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}{W}");

        // Create two 4/4 white Angel Warrior creature tokens with flying. Non-Angel creatures you control gain indestructible until your next turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelWarriorToken(), 2));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(),
                Duration.UntilYourNextTurn, filter
        ));
    }

    private EmeriasCall(final EmeriasCall card) {
        super(card);
    }

    @Override
    public EmeriasCall copy() {
        return new EmeriasCall(this);
    }
}
