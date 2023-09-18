
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
/**
 *
 * @author fireshoes
 */
public final class MarduShadowspear extends CardImpl {

    public MarduShadowspear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Mardu Shadowspear attacks, each opponent loses 1 life.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(1),false));
        // Dash {1}{B}
        this.addAbility(new DashAbility("{1}{B}"));
    }

    private MarduShadowspear(final MarduShadowspear card) {
        super(card);
    }

    @Override
    public MarduShadowspear copy() {
        return new MarduShadowspear(this);
    }
}