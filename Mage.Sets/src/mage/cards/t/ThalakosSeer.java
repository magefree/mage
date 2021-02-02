
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ThalakosSeer extends CardImpl {

    public ThalakosSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.THALAKOS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // When Thalakos Seer leaves the battlefield, draw a card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private ThalakosSeer(final ThalakosSeer card) {
        super(card);
    }

    @Override
    public ThalakosSeer copy() {
        return new ThalakosSeer(this);
    }
}
