package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SculptedSunburst extends CardImpl {

    public SculptedSunburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose a creature you control, then each opponent chooses a creature they control with equal or lesser power. If you chose a creature this way, exile each creature not chosen by any player this way.
        this.getSpellAbility().addEffect(new SculptedSunburstEffect());
    }

    private SculptedSunburst(final SculptedSunburst card) {
        super(card);
    }

    @Override
    public SculptedSunburst copy() {
        return new SculptedSunburst(this);
    }
}

class SculptedSunburstEffect extends OneShotEffect {

    SculptedSunburstEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature you control, then each opponent chooses " +
                "a creature they control with equal or lesser power. If you chose a creature this way, " +
                "exile each creature not chosen by any player this way";
    }

    private SculptedSunburstEffect(final SculptedSunburstEffect effect) {
        super(effect);
    }

    @Override
    public SculptedSunburstEffect copy() {
        return new SculptedSunburstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Set<UUID> set = new HashSet<>();
        set.add(permanent.getId());
        int power = permanent.getPower().getValue();
        FilterPermanent filter = new FilterControlledCreaturePermanent(
                "creature you control with power " + power + " or less"
        );
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, power + 1));
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (!game.getBattlefield().contains(filter, source, game, 1)) {
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            TargetPermanent targetPermanent = new TargetPermanent(filter);
            targetPermanent.setNotTarget(true);
            opponent.choose(outcome, targetPermanent, source, game);
            set.add(targetPermanent.getFirstTarget());
        }
        Cards cards = new CardsImpl(game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        ));
        cards.removeIf(set::contains);
        player.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }
}
