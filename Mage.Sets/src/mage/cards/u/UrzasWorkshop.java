package mage.cards.u;

import mage.Mana;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzasWorkshop extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent(
            SubType.URZAS, "Urza's land you control. Activate only if you control three or more artifacts"
    ));
    private static final Hint hint = new ValueHint("Urza's lands you control", xValue);

    public UrzasWorkshop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.URZAS);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Metalcraft -- {T}: Add {C} for each Urza's land you control. Activate only if you control three or more artifacts.
        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana(1), xValue, (String) null)
                .setCondition(MetalcraftCondition.instance)
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
                .addHint(hint));
    }

    private UrzasWorkshop(final UrzasWorkshop card) {
        super(card);
    }

    @Override
    public UrzasWorkshop copy() {
        return new UrzasWorkshop(this);
    }
}
