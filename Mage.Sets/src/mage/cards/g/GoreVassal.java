
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class GoreVassal extends CardImpl {

    public GoreVassal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice Gore Vassal: Put a -1/-1 counter on target creature. Then if that creature's toughness is 1 or greater, regenerate it.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()),
                new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GoreVassalEffect());
        this.addAbility(ability);
    }

    private GoreVassal(final GoreVassal card) {
        super(card);
    }

    @Override
    public GoreVassal copy() {
        return new GoreVassal(this);
    }
}

class GoreVassalEffect extends RegenerateTargetEffect {

    GoreVassalEffect() {
        super();
        staticText = "Then if that creature's toughness is 1 or greater, regenerate it";
    }

    @Override
    public void init(Ability source, Game game) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null || creature.getToughness().getValue() < 1) {
            this.discard();
            return;
        }
        super.init(source, game);
    }

}
