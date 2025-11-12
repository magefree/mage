package mage.cards.s;

import mage.Mana;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class SanctumOfFruitfulHarvest extends CardImpl {

    public SanctumOfFruitfulHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, add X mana of any one color, where X is the number of Shrines you control.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new DynamicManaEffect(
                Mana.AnyMana(1), ShrinesYouControlCount.WHERE_X,
                "add X mana of any one color, where X is the number of Shrines you control", true
        )).addHint(ShrinesYouControlCount.getHint()));
    }

    private SanctumOfFruitfulHarvest(final SanctumOfFruitfulHarvest card) {
        super(card);
    }

    @Override
    public SanctumOfFruitfulHarvest copy() {
        return new SanctumOfFruitfulHarvest(this);
    }
}
