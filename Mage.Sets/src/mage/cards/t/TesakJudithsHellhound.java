package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TesakJudithsHellhound extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent(SubType.DOG, "Dogs");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("Creatures you control with counters on them");
    private static final FilterCard filter3 = new FilterCreatureCard();

    static {
        filter2.add(CounterAnyPredicate.instance);
        filter3.add(SubType.DOG.getPredicate());
    }

    public TesakJudithsHellhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL, SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Unleash
        this.addAbility(new UnleashAbility());

        // Other Dogs you control have unleash.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new UnleashAbility(), Duration.WhileOnBattlefield, filter1, true
        ));
        ability.addEffect(new GainAbilityControlledSpellsEffect(new UnleashAbility(), filter3).setText(""));
        this.addAbility(ability);

        // Creatures you control with counters on them have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        )));

        // Whenever Tesak, Judith's Hellhound attacks, add {R} for each attacking creature.
        this.addAbility(new AttacksTriggeredAbility(
                new DynamicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURE))
        ));
    }

    private TesakJudithsHellhound(final TesakJudithsHellhound card) {
        super(card);
    }

    @Override
    public TesakJudithsHellhound copy() {
        return new TesakJudithsHellhound(this);
    }
}
