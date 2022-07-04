package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ThelonsChant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public ThelonsChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // At the beginning of your upkeep, sacrifice Thelon's Chant unless you pay {G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{G}")), TargetController.YOU, false));

        // Whenever a player puts a Swamp onto the battlefield, Thelon's Chant deals 3 damage to that player unless they put a -1/-1 counter on a creature they control.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new ThelonsChantEffect(), filter, false, SetTargetPointer.PLAYER,
                "Whenever a player puts a Swamp onto the battlefield, {this} deals 3 damage to that player unless they put a -1/-1 counter on a creature they control."));
    }

    private ThelonsChant(final ThelonsChant card) {
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
        staticText = "{this} deals 3 damage to that player unless they put a -1/-1 counter on a creature they control";
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
                    && player.choose(Outcome.UnboostCreature, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.addCounters(CounterType.M1M1.createInstance(), player.getId(), source, game);
                    paid = true;
                }
            }
            if (!paid) {
                player.damage(3, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
