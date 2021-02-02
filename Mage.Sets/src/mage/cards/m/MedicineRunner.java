
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MedicineRunner extends CardImpl {

    public MedicineRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Medicine Runner enters the battlefield, you may remove a counter from target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RemoveCounterTargetEffect(), true);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private MedicineRunner(final MedicineRunner card) {
        super(card);
    }

    @Override
    public MedicineRunner copy() {
        return new MedicineRunner(this);
    }
}
