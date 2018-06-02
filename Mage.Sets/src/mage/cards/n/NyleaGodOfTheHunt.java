
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
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
        // As long as your devotion to white is less than five, Nylea isn't a creature.<i>(Each {G} in the mana costs of permanents you control counts towards your devotion to green.)</i>
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.G), 5);
        effect.setText("As long as your devotion to green is less than five, Nylea isn't a creature.<i>(Each {G} in the mana costs of permanents you control counts towards your devotion to green.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, true)));
        // {3}{G}: Target creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl("{3}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public NyleaGodOfTheHunt(final NyleaGodOfTheHunt card) {
        super(card);
    }

    @Override
    public NyleaGodOfTheHunt copy() {
        return new NyleaGodOfTheHunt(this);
    }
}
