package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.*;

/**
 * @author TheElk801
 */
public final class NeganTheColdBlooded extends CardImpl {

    public NeganTheColdBlooded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Negan enters the battlefield, you and target opponent each secretly choose a creature that player controls. Then those choices are revealed, and that player sacrifices those creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NeganTheColdBloodedEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever an opponent sacrifices a creature, you create a Treasure token.
        this.addAbility(new SacrificeAllTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()).setText("you create a Treasure token"),
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                TargetController.OPPONENT, false
        ));
    }

    private NeganTheColdBlooded(final NeganTheColdBlooded card) {
        super(card);
    }

    @Override
    public NeganTheColdBlooded copy() {
        return new NeganTheColdBlooded(this);
    }
}

class NeganTheColdBloodedEffect extends OneShotEffect {

    NeganTheColdBloodedEffect() {
        super(Outcome.Benefit);
        staticText = "you and target opponent each secretly choose a creature that player controls. " +
                "Then those choices are revealed, and that player sacrifices those creatures";
    }

    private NeganTheColdBloodedEffect(final NeganTheColdBloodedEffect effect) {
        super(effect);
    }

    @Override
    public NeganTheColdBloodedEffect copy() {
        return new NeganTheColdBloodedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getName());
        filter.add(new ControllerIdPredicate(opponent.getId()));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        Set<UUID> choices = new LinkedHashSet<>();
        controller.choose(Outcome.DestroyPermanent, target, source, game);
        UUID controllerChoice = target.getFirstTarget();
        choices.add(controllerChoice);

        target.clearChosen();
        opponent.choose(Outcome.DestroyPermanent, target, source, game);
        UUID opponentChoice = target.getFirstTarget();
        choices.add(opponentChoice);

        for (UUID creatureId : choices) {
            Permanent permanent = game.getPermanent(creatureId);
            if (permanent == null) {
                continue;
            }
            if (Objects.equals(controllerChoice, creatureId)) {
                game.informPlayers(controller.getLogName() + " chose " + permanent.getIdName());
            }
            if (Objects.equals(opponentChoice, creatureId)) {
                game.informPlayers(opponent.getLogName() + " chose " + permanent.getIdName());
            }
            permanent.sacrifice(source, game);
        }
        return true;
    }
}
