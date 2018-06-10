
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class ThelonsChant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Swamp");

    static{
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public ThelonsChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // At the beginning of your upkeep, sacrifice Thelon's Chant unless you pay {G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{G}")), TargetController.YOU, false));

        // Whenever a player puts a Swamp onto the battlefield, Thelon's Chant deals 3 damage to that player unless he or she puts a -1/-1 counter on a creature he or she controls.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new ThelonsChantEffect(), filter, false, SetTargetPointer.PLAYER, 
                "Whenever a player puts a Swamp onto the battlefield, {this} deals 3 damage to that player unless he or she puts a -1/-1 counter on a creature he or she controls."));
    }

    public ThelonsChant(final ThelonsChant card) {
        super(card);
    }

    @Override
    public ThelonsChant copy() {
        return new ThelonsChant(this);
    }
}

class ThelonsChantEffect extends OneShotEffect {

    public ThelonsChantEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to that player unless he or she puts a -1/-1 counter on a creature he or she controls";
    }

    public ThelonsChantEffect(final ThelonsChantEffect effect) {
        super(effect);
    }

    @Override
    public ThelonsChantEffect copy() {
        return new ThelonsChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            boolean paid = false;
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            if (player.chooseUse(Outcome.Detriment, "Put a -1/-1 counter on a creature you control? (otherwise " + sourcePermanent.getLogName() + " deals 3 damage to you)", source, game)
                    && player.choose(Outcome.UnboostCreature, target, source.getSourceId(), game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.addCounters(CounterType.M1M1.createInstance(), source, game);
                    paid = true;
                }
            }
            if (!paid) {
                player.damage(3, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
