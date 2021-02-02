
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class WellOfIdeas extends CardImpl {

    public WellOfIdeas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{U}");


        // When Well of Ideas enters the battlefield, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2), false));

        // At the beginning of each other player's draw step, that player draws an additional card.
        Effect effect  = new DrawCardTargetEffect(1);
        effect.setText("that player draws an additional card");
        this.addAbility(new BeginningOfDrawTriggeredAbility(effect, TargetController.NOT_YOU, false));

        // At the beginning of your draw step, draw two additional cards.
        effect  = new DrawCardTargetEffect(2);
        effect.setText("draw two additional cards");
        this.addAbility(new BeginningOfDrawTriggeredAbility(effect , TargetController.YOU, false));
    }

    private WellOfIdeas(final WellOfIdeas card) {
        super(card);
    }

    @Override
    public WellOfIdeas copy() {
        return new WellOfIdeas(this);
    }
}
