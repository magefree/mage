
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ImmaculateMagistrate extends CardImpl {

    public ImmaculateMagistrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Put a +1/+1 counter on target creature for each Elf you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ImmaculateMagistrateEffect(),
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ImmaculateMagistrate(final ImmaculateMagistrate card) {
        super(card);
    }

    @Override
    public ImmaculateMagistrate copy() {
        return new ImmaculateMagistrate(this);
    }
}

class ImmaculateMagistrateEffect extends OneShotEffect {
    static final FilterControlledPermanent filter = new FilterControlledPermanent("Elf");
    static {
        filter.add(SubType.ELF.getPredicate());
    }
    public ImmaculateMagistrateEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put a +1/+1 counter on target creature for each Elf you control";
    }

    public ImmaculateMagistrateEffect(final ImmaculateMagistrateEffect effect) {
        super(effect);
    }

    @Override
    public ImmaculateMagistrateEffect copy() {
        return new ImmaculateMagistrateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            if (count > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(count), source.getControllerId(), source, game);
                return true;
            }
        }
        return false;
    }
}
