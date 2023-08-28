
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class WildMammoth extends CardImpl {

    public WildMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if a player controls more creatures than each other player, the player who controls the most creatures gains control of Wild Mammoth.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WildMammothEffect(), TargetController.YOU, false));
    }

    private WildMammoth(final WildMammoth card) {
        super(card);
    }

    @Override
    public WildMammoth copy() {
        return new WildMammoth(this);
    }
}

class WildMammothEffect extends OneShotEffect {

    public WildMammothEffect() {
        super(Outcome.GainControl);
        this.staticText = "if a player controls more creatures than each other player, the player who controls the most creatures gains control of {this}";
    }

    private WildMammothEffect(final WildMammothEffect effect) {
        super(effect);
    }

    @Override
    public WildMammothEffect copy() {
        return new WildMammothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                Player newController = null;
                int maxCreatures = 0;
                boolean tie = false;
                FilterPermanent filter = new FilterPermanent();
                filter.add(CardType.CREATURE.getPredicate());
                
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        int value = game.getBattlefield().countAll(filter, playerId, game);
                        if (value > maxCreatures) {
                            maxCreatures = value;
                        }
                    }
                }
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        int value = game.getBattlefield().countAll(filter, playerId, game);
                        if (value == maxCreatures) {
                            if (newController == null) {
                                newController = player;
                            } else {
                                tie = true;
                                break;
                            }
                        }
                    }
                }
                
                if (!controller.equals(newController) && !tie && newController != null) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
                    effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;

    }
}
