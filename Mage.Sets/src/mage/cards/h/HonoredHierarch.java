package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.RenownAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HonoredHierarch extends CardImpl {

    public HonoredHierarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Renown 1 <i>(When this creature deals combat damage to a player, if it isn't renowned put a +1/+1 counter on it and it becomes renowned.)<i>
        this.addAbility(new RenownAbility(1));

        // As long as Honored Hierarch is renowned, it has vigilance and "{T}: Add one mana of any color."
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield),
                RenownedSourceCondition.THIS, "as long as {this} is renowned, it has vigilance"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new AnyColorManaAbility(), Duration.WhileOnBattlefield),
                RenownedSourceCondition.THIS, "and \"{T}: Add one mana of any color.\""
        ));
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
