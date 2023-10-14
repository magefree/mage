
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.BoostCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Derpthemeus
 */
public final class LivingArmor extends CardImpl {

    public LivingArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {tap}, Sacrifice Living Armor: Put X +0/+1 counters on target creature, where X is that creature's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LivingArmorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LivingArmor(final LivingArmor card) {
        super(card);
    }

    @Override
    public LivingArmor copy() {
        return new LivingArmor(this);
    }

    static class LivingArmorEffect extends OneShotEffect {

        public LivingArmorEffect() {
            super(Outcome.BoostCreature);
            this.staticText = "Put X +0/+1 counters on target creature, where X is that creature's mana value";
        }

        private LivingArmorEffect(final LivingArmorEffect effect) {
            super(effect);
        }

        @Override
        public LivingArmorEffect copy() {
            return new LivingArmorEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature != null) {
                int amount = creature.getManaValue();
                creature.addCounters(new BoostCounter(0, 1, amount), source.getControllerId(), source, game);
                return true;
            }
            return false;
        }
    }
}
