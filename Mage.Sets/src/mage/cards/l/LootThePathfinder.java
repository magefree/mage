package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
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
        this.addAbility(new ExhaustAbility(new AddManaOfAnyColorEffect(3),
                new CompositeCost(new ManaCostsImpl<>("{G}"), new TapSourceCost(), "{G}, {T}")));
        // Exhaust -- {U}, {T}: Draw three cards.
        this.addAbility(new ExhaustAbility(new DrawCardSourceControllerEffect(3),
                new CompositeCost(new ManaCostsImpl<>("{U}"), new TapSourceCost(), "{U}, {T}"),false));
        // Exhaust -- {R}, {T}: Loot deals 3 damage to any target.
        this.addAbility(new ExhaustAbility(new DamageTargetEffect(3),
                new CompositeCost(new ManaCostsImpl<>("{R}"), new TapSourceCost(), "{R}, {T}"), false));
    }

    private LootThePathfinder(final LootThePathfinder card) {
        super(card);
    }

    @Override
    public LootThePathfinder copy() {
        return new LootThePathfinder(this);
    }
}
