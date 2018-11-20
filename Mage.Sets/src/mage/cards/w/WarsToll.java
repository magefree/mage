package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class WarsToll extends CardImpl {

    private final static FilterCreaturePermanent filterOpponentCreature = new FilterCreaturePermanent("creature an opponent controls");
    private final static FilterLandPermanent filterOpponentLand = new FilterLandPermanent("an opponent taps a land");

    static {
        filterOpponentCreature.add(new ControllerPredicate(TargetController.OPPONENT));
        filterOpponentLand.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public WarsToll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever an opponent taps a land for mana, tap all lands that player controls.
        this.addAbility(new TapForManaAllTriggeredAbility(new WarsTollTapEffect(), filterOpponentLand, SetTargetPointer.PLAYER));

        // If a creature an opponent controls attacks, all creatures that opponent controls attack if able.
        this.addAbility(new AttacksAllTriggeredAbility(new WarsTollEffect(), false, filterOpponentCreature, SetTargetPointer.PERMANENT, true));

    }

    public WarsToll(final WarsToll card) {
        super(card);
    }

    @Override
    public WarsToll copy() {
        return new WarsToll(this);
    }
}

class WarsTollTapEffect extends OneShotEffect {

    public WarsTollTapEffect() {
        super(Outcome.Tap);
        staticText = "tap all lands that player controls";
    }

    public WarsTollTapEffect(final WarsTollTapEffect effect) {
        super(effect);
    }

    @Override
    public WarsTollTapEffect copy() {
        return new WarsTollTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getTargetPointer().getFirst(game, source) != null) {
            game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LAND, getTargetPointer().getFirst(game, source), game).forEach((permanent) -> {
                permanent.tap(game);
            });
            return true;
        }
        return false;
    }
}

class WarsTollEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filterOpponentCreatures = new FilterCreaturePermanent();

    public WarsTollEffect() {
        super(Outcome.Neutral);
        staticText = "all creatures that opponent controls attack if able";
    }

    public WarsTollEffect(final WarsTollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(game.getPermanent(getTargetPointer().getFirst(game, source)).getControllerId());
        if (opponent != null) {
            filterOpponentCreatures.add(new ControllerIdPredicate(opponent.getId()));
            game.getBattlefield().getAllActivePermanents(CardType.CREATURE).stream().filter((permanent) -> (filterOpponentCreatures.match(permanent, source.getSourceId(), source.getControllerId(), game))).forEachOrdered((permanent) -> {
                //TODO: allow the player to choose between a planeswalker and player
                if (permanent.canAttack(source.getControllerId(), game)) {
                    opponent.declareAttacker(permanent.getId(), source.getControllerId(), game, false);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public WarsTollEffect copy() {
        return new WarsTollEffect(this);
    }
}
