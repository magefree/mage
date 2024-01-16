
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author LevelX2
 */
public final class WoebringerDemon extends CardImpl {

    public WoebringerDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each player's upkeep, that player sacrifices a creature. 
        // If the player can't, sacrifice Woebringer Demon.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, 
                new WoebringerDemonEffect(), TargetController.ANY, false, true));
    }

    private WoebringerDemon(final WoebringerDemon card) {
        super(card);
    }

    @Override
    public WoebringerDemon copy() {
        return new WoebringerDemon(this);
    }
}

class WoebringerDemonEffect extends OneShotEffect {

    WoebringerDemonEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player sacrifices a creature. If the player can't, sacrifice {this}";
    }

    private WoebringerDemonEffect(final WoebringerDemonEffect effect) {
        super(effect);
    }

    @Override
    public WoebringerDemonEffect copy() {
        return new WoebringerDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player currentPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (currentPlayer != null) {
                TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_PERMANENT_CREATURE);
                if (target.canChoose(currentPlayer.getId(), source, game)) {
                    currentPlayer.choose(Outcome.Sacrifice, target, source, game);
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.sacrifice(source, game);
                        return true;
                    }
                }
            }
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (sourceObject != null && sourceObject.getZoneChangeCounter(game) == source.getSourceObjectZoneChangeCounter()) {
                sourceObject.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }
}
