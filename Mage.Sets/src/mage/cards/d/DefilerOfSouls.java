
package mage.cards.d;

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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DefilerOfSouls extends CardImpl {

    public DefilerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{R}");
        this.subtype.add(SubType.DEMON);


        
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of each player's upkeep, that player sacrifices a monocolored creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DefilerOfSoulsEffect(), TargetController.ANY, false, true));
    }

    private DefilerOfSouls(final DefilerOfSouls card) {
        super(card);
    }

    @Override
    public DefilerOfSouls copy() {
        return new DefilerOfSouls(this);
    }
}

class DefilerOfSoulsEffect extends OneShotEffect {

    DefilerOfSoulsEffect() {
        super(Outcome.Sacrifice);
        staticText = "that player sacrifices a monocolored creature";
    }

    DefilerOfSoulsEffect(final DefilerOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("monocolored creature");
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        filter.add(MonocoloredPredicate.instance);
        
        int amount;
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        amount = Math.min(1, realCount);
        
        Target target = new TargetControlledPermanent(amount, amount, filter, false);
        target.setNotTarget(true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (amount > 0 && target.canChoose(player.getId(), source, game)) {
            boolean abilityApplied = false;
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), source, game)) {
                player.choose(Outcome.Sacrifice, target, source, game);
            }

            for ( int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent(target.getTargets().get(idx));

                if ( permanent != null ) {
                    abilityApplied |= permanent.sacrifice(source, game);
                }
            }

            return abilityApplied;
        }
        return false;
    }

    @Override
    public DefilerOfSoulsEffect copy() {
        return new DefilerOfSoulsEffect(this);
    }
}