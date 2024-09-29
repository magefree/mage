package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ZoyowasJustice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature with mana value 1 or greater");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 1));
    }

    public ZoyowasJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // The owner of target artifact or creature with mana value 1 or greater shuffles it into their library. Then that player discovers X, where X is its mana value.
        this.getSpellAbility().addEffect(new ZoyowasJusticeEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ZoyowasJustice(final ZoyowasJustice card) {
        super(card);
    }

    @Override
    public ZoyowasJustice copy() {
        return new ZoyowasJustice(this);
    }
}

class ZoyowasJusticeEffect extends OneShotEffect {

    ZoyowasJusticeEffect() {
        super(Outcome.Neutral);
        staticText = "the owner of target artifact or creature with mana value 1 or greater shuffles it into their library. "
                + "Then that player discovers X, where X is its mana value";
    }

    private ZoyowasJusticeEffect(final ZoyowasJusticeEffect effect) {
        super(effect);
    }

    @Override
    public ZoyowasJusticeEffect copy() {
        return new ZoyowasJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int value = permanent.getManaValue();
        Player owner = game.getPlayer(permanent.getOwnerId());
        if (owner == null) {
            return false;
        }

        owner.shuffleCardsToLibrary(permanent, game, source);
        DiscoverEffect.doDiscover(owner, value, game, source);
        return true;
    }

}