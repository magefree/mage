package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Controllable;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatterTheSky extends CardImpl {

    public ShatterTheSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Each player who controls a creature with power 4 or greater draws a card. Then destroy all creatures.
        this.getSpellAbility().addEffect(new ShatterTheSkyEffect());
    }

    private ShatterTheSky(final ShatterTheSky card) {
        super(card);
    }

    @Override
    public ShatterTheSky copy() {
        return new ShatterTheSky(this);
    }
}

class ShatterTheSkyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final Effect effect = new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE);

    ShatterTheSkyEffect() {
        super(Outcome.Benefit);
        staticText = "each player who controls a creature with power 4 or greater draws a card. " +
                "Then destroy all creatures";
    }

    private ShatterTheSkyEffect(final ShatterTheSkyEffect effect) {
        super(effect);
    }

    @Override
    public ShatterTheSkyEffect copy() {
        return new ShatterTheSkyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(Controllable::getControllerId)
                .distinct()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> player.drawCards(1, source, game));
        effect.apply(game, source);
        return true;
    }
}