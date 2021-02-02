
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class SoltariTrooper extends CardImpl {

    public SoltariTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.SOLTARI);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever Soltari Trooper attacks, it gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(1,1,Duration.EndOfTurn), false));
    }

    private SoltariTrooper(final SoltariTrooper card) {
        super(card);
    }

    @Override
    public SoltariTrooper copy() {
        return new SoltariTrooper(this);
    }
}
