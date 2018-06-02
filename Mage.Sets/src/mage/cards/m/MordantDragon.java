
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MordantDragon extends CardImpl {

    public MordantDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{R}: Mordant Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{1}{R}")));

        // Whenever Mordant Dragon deals combat damage to a player, you may have it deal that much damage to target creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MordantDragonEffect(), true, true));
    }

    public MordantDragon(final MordantDragon card) {
        super(card);
    }

    @Override
    public MordantDragon copy() {
        return new MordantDragon(this);
    }
}

class MordantDragonEffect extends OneShotEffect {

    public MordantDragonEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to target creature that player controls";
    }

    public MordantDragonEffect(final MordantDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + player.getLogName() + " controls");
                filter.add(new ControllerIdPredicate(player.getId()));
                TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
                if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Damage, source.getControllerId(), source.getSourceId(), game)) {
                    UUID creature = target.getFirstTarget();
                    if (creature != null) {
                        game.getPermanent(creature).damage(amount, source.getSourceId(), game, false, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public MordantDragonEffect copy() {
        return new MordantDragonEffect(this);
    }
}
