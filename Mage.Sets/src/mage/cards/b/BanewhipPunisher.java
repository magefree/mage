package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

public final class BanewhipPunisher extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a -1/-1 counter on it");    
    
    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public BanewhipPunisher(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        subtype.add(SubType.HUMAN, SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
        
        // When Banewhip Punisher enters the battlefield, you may put a -1/-1 counter on target creature.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(1)), true);
        etbAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(etbAbility);
        
        // {B}, sacrifice Banewhip Punisher: Destroy target creature that has a -1/-1 counter on it.
        Ability destroyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{B}"));
        destroyAbility.addCost(new SacrificeSourceCost());
        destroyAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(destroyAbility);
        
    }

    public BanewhipPunisher(final BanewhipPunisher banewhipPunisher) {
        super(banewhipPunisher);
    }

    public BanewhipPunisher copy() {
        return new BanewhipPunisher(this);
    }
}
