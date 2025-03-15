package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Jmlundeen
 */
public final class LootThePathfinder extends CardImpl {

    public LootThePathfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Exhaust -- {G}, {T}: Add three mana of any one color.
        Ability abilityOne = new ExhaustAbility(new AddManaOfAnyColorEffect(3), new ManaCostsImpl<>("{G}"));
        abilityOne.addCost(new TapSourceCost());
        this.addAbility(abilityOne);

        // Exhaust -- {U}, {T}: Draw three cards.
        Ability abilityTwo = new ExhaustAbility(new DrawCardSourceControllerEffect(3), new ManaCostsImpl<>("{U}"), false);
        abilityTwo.addCost(new TapSourceCost());
        this.addAbility(abilityTwo);

        // Exhaust -- {R}, {T}: Loot deals 3 damage to any target.
        Ability abilityThree = new ExhaustAbility(new DamageTargetEffect(3), new ManaCostsImpl<>("{R}"), false);
        abilityThree.addCost(new TapSourceCost());
        abilityThree.addTarget(new TargetAnyTarget());
        this.addAbility(abilityThree);
    }

    private LootThePathfinder(final LootThePathfinder card) {
        super(card);
    }

    @Override
    public LootThePathfinder copy() {
        return new LootThePathfinder(this);
    }
}
