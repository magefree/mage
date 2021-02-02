
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MilitaryIntelligence extends CardImpl {

    public MilitaryIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // Whenever you attack with two or more creatures, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 2));
    }

    private MilitaryIntelligence(final MilitaryIntelligence card) {
        super(card);
    }

    @Override
    public MilitaryIntelligence copy() {
        return new MilitaryIntelligence(this);
    }
}
