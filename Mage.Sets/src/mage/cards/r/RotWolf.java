
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RotWolf extends CardImpl {

    public RotWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(InfectAbility.getInstance());
        // Whenever a creature dealt damage by Rot Wolf this turn dies, you may draw a card.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private RotWolf(final RotWolf card) {
        super(card);
    }

    @Override
    public RotWolf copy() {
        return new RotWolf(this);
    }
}
