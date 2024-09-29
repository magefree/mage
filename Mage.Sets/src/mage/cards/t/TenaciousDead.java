
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TenaciousDead extends CardImpl {

    public TenaciousDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Tenacious Dead dies, you may pay {1}{B}. If you do, return it to the battlefield tapped under its owner's control.
        Effect effect = new DoIfCostPaid(new ReturnToBattlefieldUnderOwnerControlSourceEffect(true)
                .setText("return it to the battlefield tapped under its owner's control"), new ManaCostsImpl<>("{1}{B}"));
        this.addAbility(new DiesSourceTriggeredAbility(effect, false));

    }

    private TenaciousDead(final TenaciousDead card) {
        super(card);
    }

    @Override
    public TenaciousDead copy() {
        return new TenaciousDead(this);
    }
}
