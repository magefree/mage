package mage.cards.k;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WhiteDogToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrovodHaunch extends CardImpl {

    public KrovodHaunch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // {2}, {T}, Sacrifice Krovod Haunch: You gain 3 life.
        this.addAbility(new FoodAbility(true));

        // When Krovod Haunch is put into a graveyard from the battlefield, you may pay {1}{W}. If you do, create two 1/1 white Dog creature tokens.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new WhiteDogToken(), 2), new ManaCostsImpl<>("{1}{W}"))
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private KrovodHaunch(final KrovodHaunch card) {
        super(card);
    }

    @Override
    public KrovodHaunch copy() {
        return new KrovodHaunch(this);
    }
}
