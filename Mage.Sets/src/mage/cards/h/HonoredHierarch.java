
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.RenownAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class HonoredHierarch extends CardImpl {

    public HonoredHierarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Renown 1 <i>(When this creature deals combat damage to a player, if it isn't renowned put a +1/+1 counter on it and it becomes renowned.)<i>
        this.addAbility(new RenownAbility(1));

        // As long as Honored Hierarch is renowned, it has vigilance and "{T}: Add one mana of any color."
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield),
                RenownedSourceCondition.instance,
                "As long as {this} is renowned, it has vigilance");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new AnyColorManaAbility(), Duration.WhileOnBattlefield),
                RenownedSourceCondition.instance,
                "and \"{T}: Add one mana of any color.\"");
        ability.addEffect(effect);
        this.addAbility(ability);        
        
    }

    private HonoredHierarch(final HonoredHierarch card) {
        super(card);
    }

    @Override
    public HonoredHierarch copy() {
        return new HonoredHierarch(this);
    }
}
