package mage.cards.s;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ForestDryadToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StaffOfTitania extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.FOREST, "Forests you control"), null
    );
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    public StaffOfTitania(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +X/+X, where X is the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)));

        // Whenever equipped creature attacks, create a 1/1 green Forest Dryad land creature token.
        this.addAbility(new AttacksAttachedTriggeredAbility(new CreateTokenEffect(new ForestDryadToken())));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private StaffOfTitania(final StaffOfTitania card) {
        super(card);
    }

    @Override
    public StaffOfTitania copy() {
        return new StaffOfTitania(this);
    }
}
