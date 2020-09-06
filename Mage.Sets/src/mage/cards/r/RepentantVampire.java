
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, Nantuko (Sengir Vampire)
 */
public final class RepentantVampire extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RepentantVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a creature dealt damage by Repentant Vampire this turn dies, put a +1/+1 counter on Repentant Vampire.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
        // Threshold - As long as seven or more cards are in your graveyard, Repentant Vampire is white and has "{tap}: Destroy target black creature."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BecomesColorSourceEffect(ObjectColor.WHITE, Duration.WhileOnBattlefield),
                new CardsInControllerGraveyardCondition(7),
                "As long as seven or more cards are in your graveyard, {this} is white"));
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield),
                new CardsInControllerGraveyardCondition(7),
                "and has \"{T}: Destroy target black creature.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public RepentantVampire(final RepentantVampire card) {
        super(card);
    }

    @Override
    public RepentantVampire copy() {
        return new RepentantVampire(this);
    }
}
