package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class WarsToll extends CardImpl {

    private static final FilterCreaturePermanent filterOpponentCreature = new FilterCreaturePermanent("creature an opponent controls");
    private static final FilterLandPermanent filterOpponentLand = new FilterLandPermanent("an opponent taps a land");

    static {
        filterOpponentCreature.add(TargetController.OPPONENT.getControllerPredicate());
        filterOpponentLand.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public WarsToll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever an opponent taps a land for mana, tap all lands that player controls.
        this.addAbility(new TapForManaAllTriggeredAbility(new WarsTollTapEffect(), filterOpponentLand, SetTargetPointer.PLAYER));

        // If a creature an opponent controls attacks, all creatures that opponent controls attack if able.
        this.addAbility(new SimpleEvasionAbility(new WarsTollAttackRestrictionEffect()));

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

class WarsTollAttackRestrictionEffect extends RestrictionEffect {

    public WarsTollAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If a creature an opponent controls attacks, all creatures that opponent controls attack if able";
    }

    public WarsTollAttackRestrictionEffect(final WarsTollAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(permanent.getControllerId());
        return controller != null && controller.hasOpponent(source.getControllerId(), game);
    }

    @Override
    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
        int creaturesAbleToAttack = 0;
        for (Permanent creaturePermanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, game.getActivePlayerId(), game)) {
            if (creaturePermanent.canAttackInPrinciple(null, game)) {
                creaturesAbleToAttack++;
            }
        }
        return numberOfAttackers == 0 || numberOfAttackers == creaturesAbleToAttack;
    }

    @Override
    public WarsTollAttackRestrictionEffect copy() {
        return new WarsTollAttackRestrictionEffect(this);
    }

}
