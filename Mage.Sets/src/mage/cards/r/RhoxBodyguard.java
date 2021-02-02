

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class RhoxBodyguard extends CardImpl {

    public RhoxBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}");


        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MONK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new ExaltedAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private RhoxBodyguard(final RhoxBodyguard card) {
        super(card);
    }

    @Override
    public RhoxBodyguard copy() {
        return new RhoxBodyguard(this);
    }

}
