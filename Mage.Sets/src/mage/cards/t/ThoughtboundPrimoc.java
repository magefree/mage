package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author awjackson
 */
public final class ThoughtboundPrimoc extends CardImpl {

    public ThoughtboundPrimoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.BIRD, SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, if a player controls more Wizards than each other player,
        // the player who controls the most Wizards gains control of Thoughtbound Primoc.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ThoughtboundPrimocEffect(), TargetController.YOU, false),
                OnePlayerHasTheMostWizards.instance,
                "At the beginning of your upkeep, if a player controls more Wizards than each other player, the player who controls the most Wizards gains control of {this}"
        ));

    }

    private ThoughtboundPrimoc(final ThoughtboundPrimoc card) {
        super(card);
    }

    @Override
    public ThoughtboundPrimoc copy() {
        return new ThoughtboundPrimoc(this);
    }
}

class ThoughtboundPrimocEffect extends OneShotEffect {

    public ThoughtboundPrimocEffect() {
        super(Outcome.GainControl);
        this.staticText = "the player who controls the most Wizards gains control of {this}";
    }

    public ThoughtboundPrimocEffect(final ThoughtboundPrimocEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtboundPrimocEffect copy() {
        return new ThoughtboundPrimocEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            return false;
        }
        Player newController = OnePlayerHasTheMostWizards.getPlayerWithMostWizards(game, source);
        if (newController != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, newController.getId());
            effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
            game.addEffect(effect, source);
            if (!source.isControlledBy(newController.getId())) {
                game.informPlayers(newController.getLogName() + " got control of " + sourcePermanent.getLogName());
            }
        }
        return true;
    }
}

enum OnePlayerHasTheMostWizards implements Condition {
    instance;

    private static final FilterPermanent filter = new FilterPermanent(SubType.WIZARD, "Wizards");

    @Override
    public boolean apply(Game game, Ability source) {
        return getPlayerWithMostWizards(game, source) != null;
    }

    public static Player getPlayerWithMostWizards(Game game, Ability source) {
        int max = Integer.MIN_VALUE;
        Player playerWithMost = null;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int wizards = game.getBattlefield().countAll(filter, playerId, game);
                if (wizards > max) {
                    max = wizards;
                    playerWithMost = player;
                } else if (wizards == max) {
                    playerWithMost = null;
                }
            }
        }
        return playerWithMost;
    }

    @Override
    public String toString() {
        return "a player controls more Wizards than each other player";
    }
}
