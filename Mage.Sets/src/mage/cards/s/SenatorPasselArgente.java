
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Styxo
 */
public final class SenatorPasselArgente extends CardImpl {

    public SenatorPasselArgente(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOORIVAR);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature dies, each opponent loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private SenatorPasselArgente(final SenatorPasselArgente card) {
        super(card);
    }

    @Override
    public SenatorPasselArgente copy() {
        return new SenatorPasselArgente(this);
    }
}
