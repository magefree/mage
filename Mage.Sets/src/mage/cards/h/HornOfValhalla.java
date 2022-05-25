package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornOfValhalla extends AdventureCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);

    public HornOfValhalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.SORCERY}, "{1}{W}", "Ysgard's Call", "{X}{W}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each other creature you control.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));

        // Ysgard's Call
        // Create X 1/1 white Soldier creature tokens.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(
                new SoldierToken(), ManacostVariableValue.REGULAR
        ));
    }

    private HornOfValhalla(final HornOfValhalla card) {
        super(card);
    }

    @Override
    public HornOfValhalla copy() {
        return new HornOfValhalla(this);
    }
}
