
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class GlacialPlating extends CardImpl {

    public GlacialPlating(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Cumulative upkeep {snow}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{S}")));

        // Enchanted creature gets +3/+3 for each age counter on Glacial Plating.
        DynamicValue boostValue = new MultipliedValue(new CountersSourceCount(CounterType.AGE), 3);
        Effect effect = new BoostEnchantedEffect(boostValue, boostValue, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +3/+3 for each age counter on {this}");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private GlacialPlating(final GlacialPlating card) {
        super(card);
    }

    @Override
    public GlacialPlating copy() {
        return new GlacialPlating(this);
    }
}
