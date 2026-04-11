package mage.cards.v;

import java.util.UUID;

import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

/**
 *
 * @author muz
 */
public final class ViciousRivalry extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures with mana value X or less");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
        filter.add(ViciousRivalryPredicate.instance);
    }

    public ViciousRivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // As an additional cost to cast this spell, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Destroy all artifacts and creatures with mana value X or less.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private ViciousRivalry(final ViciousRivalry card) {
        super(card);
    }

    @Override
    public ViciousRivalry copy() {
        return new ViciousRivalry(this);
    }
}

enum ViciousRivalryPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
