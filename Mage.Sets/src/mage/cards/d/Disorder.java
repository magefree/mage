package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LoneFox
 */
public final class Disorder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Disorder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Disorder deals 2 damage to each white creature and each player who controls a white creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
        this.getSpellAbility().addEffect(new DisorderEffect());
    }

    private Disorder(final Disorder card) {
        super(card);
    }

    @Override
    public Disorder copy() {
        return new Disorder(this);
    }
}

class DisorderEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public DisorderEffect() {
        super(Outcome.Damage);
        this.staticText = "and each player who controls a white creature";
    }

    public DisorderEffect(final DisorderEffect effect) {
        super(effect);
    }

    @Override
    public DisorderEffect copy() {
        return new DisorderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)
                .stream()
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .distinct()
                .map(game::getPlayer)
                .collect(Collectors.toList())) {
            player.damage(2, source.getSourceId(), source, game);
        }
        return true;
    }
}
