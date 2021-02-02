
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JungleBarrier extends CardImpl {

    public JungleBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);


        this.power = new MageInt(2);
        this.toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private JungleBarrier(final JungleBarrier card) {
        super(card);
    }

    @Override
    public JungleBarrier copy() {
        return new JungleBarrier(this);
    }
}
