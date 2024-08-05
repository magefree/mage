package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManifoldMouse extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOUSE);

    public ManifoldMouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // At the beginning of combat on your turn, target Mouse you control gains your choice of double strike or trample until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new ManifoldMouseEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ManifoldMouse(final ManifoldMouse card) {
        super(card);
    }

    @Override
    public ManifoldMouse copy() {
        return new ManifoldMouse(this);
    }
}

class ManifoldMouseEffect extends OneShotEffect {

    ManifoldMouseEffect() {
        super(Outcome.Benefit);
        staticText = "target Mouse you control gains your choice of double strike or trample until end of turn";
    }

    private ManifoldMouseEffect(final ManifoldMouseEffect effect) {
        super(effect);
    }

    @Override
    public ManifoldMouseEffect copy() {
        return new ManifoldMouseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Ability ability = player.chooseUse(
                outcome, "Double strike or trample?", null,
                "Double strike", "Trample", source, game
        ) ? DoubleStrikeAbility.getInstance() : TrampleAbility.getInstance();
        game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn), source);
        return true;
    }
}
