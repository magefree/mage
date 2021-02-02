
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AuroraOfEmrakul extends CardImpl {

    public AuroraOfEmrakul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.REFLECTION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Aurora of Emrakul attacks, each opponent loses 3 life.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(3),false));
    }

    private AuroraOfEmrakul(final AuroraOfEmrakul card) {
        super(card);
    }

    @Override
    public AuroraOfEmrakul copy() {
        return new AuroraOfEmrakul(this);
    }
}
