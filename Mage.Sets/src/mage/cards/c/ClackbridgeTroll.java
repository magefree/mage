package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoatToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClackbridgeTroll extends CardImpl {

    public ClackbridgeTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Clackbridge Troll enters the battlefield, target opponent creates three 0/1 white Goat creature tokens.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new GoatToken(), 3));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // At the beginning of combat on your turn, any opponent may sacrifice a creature. If a player does, tap Clackbridge Troll, you gain 3 life, and you draw a card.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new ClackbridgeTrollEffect(), TargetController.YOU, false)
        );
    }

    private ClackbridgeTroll(final ClackbridgeTroll card) {
        super(card);
    }

    @Override
    public ClackbridgeTroll copy() {
        return new ClackbridgeTroll(this);
    }
}

class ClackbridgeTrollEffect extends OneShotEffect {

    ClackbridgeTrollEffect() {
        super(Outcome.Benefit);
        staticText = "any opponent may sacrifice a creature. If a player does, " +
                "tap {this}, you gain 3 life, and you draw a card.";
    }

    private ClackbridgeTrollEffect(final ClackbridgeTrollEffect effect) {
        super(effect);
    }

    @Override
    public ClackbridgeTrollEffect copy() {
        return new ClackbridgeTrollEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePerm = game.getPermanent(source.getSourceId());
        if (controller == null) {
            return false;
        }
        boolean flag = false;
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterControlledPermanent filter = new FilterControlledPermanent("creature to sacrifice");
            filter.add(new CardTypePredicate(CardType.CREATURE));
            filter.add(new ControllerPredicate(TargetController.YOU));
            TargetControlledPermanent target = new TargetControlledPermanent(filter);
            target.setNotTarget(true);
            if (!target.canChoose(opponent.getId(), game)
                    || !opponent.chooseUse(Outcome.AIDontUseIt, "Sacrifice a creature?", source, game)
                    || !opponent.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                continue;
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null || !permanent.sacrifice(source.getSourceId(), game)) {
                continue;
            }
            flag = true;
        }
        if (flag) {
            if (sourcePerm != null) {
                sourcePerm.tap(game);
            }
            controller.gainLife(3, game, source);
            controller.drawCards(1, game);
        }
        return true;
    }
}