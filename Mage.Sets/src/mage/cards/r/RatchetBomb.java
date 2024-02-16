

package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class RatchetBomb extends CardImpl {

    public RatchetBomb (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        
        // {T}: Put a charge counter on Ratchet Bomb.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost()));
        
        // {T}, Sacrifice Ratchet Bomb: Destroy each nonland permanent with a converted mana cost equal to the number of charge counters on Ratchet Bomb.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RatchetBombEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RatchetBomb(final RatchetBomb card) {
        super(card);
    }

    @Override
    public RatchetBomb copy() {
        return new RatchetBomb(this);
    }

    class RatchetBombEffect extends OneShotEffect {

        public RatchetBombEffect() {
            super(Outcome.DestroyPermanent);
            staticText = "Destroy each nonland permanent with mana value equal to the number of charge counters on {this}";
        }

        private RatchetBombEffect(final RatchetBombEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent p = game.getBattlefield().getPermanent(source.getSourceId());
            if (p == null) {
                p = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
                if (p == null) {
                    return false;
                }
            }

            int count = p.getCounters(game).getCount(CounterType.CHARGE);
            for (Permanent perm: game.getBattlefield().getAllActivePermanents()) {
                if (perm.getManaValue() == count && !(perm.isLand(game))) {
                    perm.destroy(source, game, false);
                }
            }

            return true;
        }

        @Override
        public RatchetBombEffect copy() {
            return new RatchetBombEffect(this);
        }

    }

}
