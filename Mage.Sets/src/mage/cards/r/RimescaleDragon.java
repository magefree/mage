
package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public final class RimescaleDragon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with ice counters");

    static {
        filter.add(CounterType.ICE.getPredicate());
    }

    public RimescaleDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        
        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{snow}: Tap target creature and put an ice counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new TapTargetEffect("tap target creature"),
                new ManaCostsImpl<>("{2}{S}")
        );
        Effect effect = new AddCountersTargetEffect(CounterType.ICE.createInstance());
        effect.setText("and put an ice counter on it");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(1));
        this.addAbility(ability);

        // Creatures with ice counters on them don't untap during their controllers' untap steps.
        effect = new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter);
        effect.setText("Creatures with ice counters on them don't untap during their controllers' untap steps");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private RimescaleDragon(final RimescaleDragon card) {
        super(card);
    }

    @Override
    public RimescaleDragon copy() {
        return new RimescaleDragon(this);
    }
}
