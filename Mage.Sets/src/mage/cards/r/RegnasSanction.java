package mage.cards.r;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RegnasSanction extends CardImpl {

    public RegnasSanction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // For each player, choose friend or foe. Each friend puts a +1/+1 counter on each creature they control. Each foe chooses one untapped creature they control, then taps the rest.
        this.getSpellAbility().addEffect(new RegnasSanctionEffect());
    }

    private RegnasSanction(final RegnasSanction card) {
        super(card);
    }

    @Override
    public RegnasSanction copy() {
        return new RegnasSanction(this);
    }
}

class RegnasSanctionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    RegnasSanctionEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe. "
                + "Each friend puts a +1/+1 counter on each creature they control. "
                + "Each foe chooses one untapped creature they control, then taps the rest";
    }

    RegnasSanctionEffect(final RegnasSanctionEffect effect) {
        super(effect);
    }

    @Override
    public RegnasSanctionEffect copy() {
        return new RegnasSanctionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        if (!choice.chooseFriendOrFoe(controller, source, game)) {
            return false;
        }
        FilterPermanent filterToTap = new FilterCreaturePermanent();
        for (Player player : choice.getFoes()) {
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            if (game.getBattlefield().contains(filter, source, game, 1)
                    && player.choose(Outcome.Benefit, target, source, game)) {
                filterToTap.add(Predicates.not(new PermanentIdPredicate(target.getFirstTarget())));
            }
        }
        choice.getFriends()
                .stream()
                .map(MageItem::getId)
                .map(ControllerIdPredicate::new)
                .map(Predicates::not)
                .forEach(filterToTap::add);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            if (choice.getFriends().stream().map(MageItem::getId).anyMatch(permanent::isControlledBy)) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return new TapAllEffect(filterToTap).apply(game, source);
    }
}
