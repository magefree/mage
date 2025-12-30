package mage.cards.j;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Hiddevb
 */
public final class Jackhammer extends CardImpl {

    public Jackhammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}{R}");
        this.subtype.add(SubType.EQUIPMENT);


        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private Jackhammer(final Jackhammer card) {
        super(card);
    }

    @Override
    public Jackhammer copy() {
        return new Jackhammer(this);
    }
}
