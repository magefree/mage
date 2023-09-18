
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class TribalGolem extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("a Beast");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("a Goblin");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent("a Soldier");
    private static final FilterControlledPermanent filter4 = new FilterControlledPermanent("a Wizard");
    private static final FilterControlledPermanent filter5 = new FilterControlledPermanent("a Zombie");

    static {
        filter1.add(SubType.BEAST.getPredicate());
        filter2.add(SubType.GOBLIN.getPredicate());
        filter3.add(SubType.SOLDIER.getPredicate());
        filter4.add(SubType.WIZARD.getPredicate());
        filter5.add(SubType.ZOMBIE.getPredicate());
    }

    public TribalGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Tribal Golem has trample as long as you control a Beast, haste as long as you control a Goblin, first strike as long as you control a Soldier, flying as long as you control a Wizard, and "{B}: Regenerate Tribal Golem" as long as you control a Zombie.
        Effect effect1 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter1),
                "{this} has trample as long as you control a Beast"
        );
        Effect effect2 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter2),
                ", haste as long as you control a Goblin"
        );
        Effect effect3 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter3),
                ", first strike as long as you control a Soldier"
        );
        Effect effect4 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter4),
                ", flying as long as you control a Wizard"
        );
        Effect effect5 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new SimpleActivatedAbility(
                        Zone.BATTLEFIELD,
                        new RegenerateSourceEffect(),
                        new ManaCostsImpl<>("{B}")
                )),
                new PermanentsOnTheBattlefieldCondition(filter5),
                ", and \"{B}: Regenerate {this}\" as long as you control a Zombie"
        );
        Ability ability = new SimpleStaticAbility(effect1);
        ability.addEffect(effect2);
        ability.addEffect(effect3);
        ability.addEffect(effect4);
        ability.addEffect(effect5);
        this.addAbility(ability);
    }

    private TribalGolem(final TribalGolem card) {
        super(card);
    }

    @Override
    public TribalGolem copy() {
        return new TribalGolem(this);
    }
}
