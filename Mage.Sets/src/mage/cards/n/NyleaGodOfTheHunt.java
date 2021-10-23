package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NyleaGodOfTheHunt extends CardImpl {

    public NyleaGodOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to green is less than five, Nylea isn't a creature.<i>(Each {G} in the mana costs of permanents you control counts towards your devotion to green.)</i>
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.G, 5))
                .addHint(DevotionCount.G.getHint()));

        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // {3}{G}: Target creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl("{3}{G}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private NyleaGodOfTheHunt(final NyleaGodOfTheHunt card) {
        super(card);
    }

    @Override
    public NyleaGodOfTheHunt copy() {
        return new NyleaGodOfTheHunt(this);
    }
}
