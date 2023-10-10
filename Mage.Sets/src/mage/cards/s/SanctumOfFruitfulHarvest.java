package mage.cards.s;

import mage.Mana;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctumOfFruitfulHarvest extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();
    private static final PermanentsOnBattlefieldCount xValue = new PermanentsOnBattlefieldCount(filter);

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    public SanctumOfFruitfulHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, add X mana of any one color, where X is the number of Shrines you control.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DynamicManaEffect(
                        Mana.AnyMana(1),
                        xValue,
                        "add X mana of any one color, where X is the number of Shrines you control",
                        true),
                TargetController.YOU, false)
                .addHint(new ValueHint("Shrines you control", xValue)));
    }

    private SanctumOfFruitfulHarvest(final SanctumOfFruitfulHarvest card) {
        super(card);
    }

    @Override
    public SanctumOfFruitfulHarvest copy() {
        return new SanctumOfFruitfulHarvest(this);
    }
}
