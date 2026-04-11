package mage.cards.f;

import java.util.UUID;

import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

/**
 *
 * @author muz
 */
public final class FixWhatsBroken extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact and creature card with mana value X");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
        filter.add(FixWhatsBrokenPredicate.instance);
    }

    public FixWhatsBroken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{B}");

        // As an additional cost to cast this spell, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Return each artifact and creature card with mana value X from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter)
            .setText("Return each artifact and creature card with mana value X from your graveyard to the battlefield")
        );
    }

    private FixWhatsBroken(final FixWhatsBroken card) {
        super(card);
    }

    @Override
    public FixWhatsBroken copy() {
        return new FixWhatsBroken(this);
    }
}

enum FixWhatsBrokenPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() == GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
