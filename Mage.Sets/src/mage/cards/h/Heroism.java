
package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class Heroism extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Heroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Sacrifice a white creature: For each attacking red creature, prevent all combat damage that would be dealt by that creature this turn unless its controller pays {2}{R}.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new HeroismEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true))));
    }

    private Heroism(final Heroism card) {
        super(card);
    }

    @Override
    public Heroism copy() {
        return new Heroism(this);
    }
}

class HeroismEffect extends OneShotEffect {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking red creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public HeroismEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each attacking red creature, prevent all combat damage that would be dealt by that creature this turn unless its controller pays {2}{R}";
    }

    public HeroismEffect(final HeroismEffect effect) {
        super(effect);
    }

    @Override
    public HeroismEffect copy() {
        return new HeroismEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(game.getActivePlayerId());
            Cost cost = new ManaCostsImpl<>("{2}{R}");
            List<Permanent> permanentsToPrevent = new ArrayList<>();
            for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game)) {
                cost.clearPaid();
                String message = "Pay " + cost.getText() + "? If you don't, " + permanent.getLogName() + "'s combat damage will be prevented this turn.";
                if (player != null) {
                    if (player.chooseUse(Outcome.Neutral, message, source, game)) {
                        if (cost.pay(source, game, source, player.getId(), false, null)) {
                            game.informPlayers(player.getLogName() + " paid " + cost.getText() + " for " + permanent.getLogName());

                        } else {
                            game.informPlayers(player.getLogName() + " didn't pay " + cost.getText() + " for " + permanent.getLogName());
                            permanentsToPrevent.add(permanent);
                        }
                    } else{
                        game.informPlayers(player.getLogName() + " didn't pay " + cost.getText() + " for " + permanent.getLogName());
                        permanentsToPrevent.add(permanent);
                    }
                }
            }

            for (Permanent permanent : permanentsToPrevent) {
                ContinuousEffect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE, true);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
