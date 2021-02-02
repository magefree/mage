
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeTargetedCardsGraveyardsEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author jeffwadsworth
 */
public final class GroundSeal extends CardImpl {

    public GroundSeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // When Ground Seal enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Cards in graveyards can't be the targets of spells or abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedCardsGraveyardsEffect()));
    }

    private GroundSeal(final GroundSeal card) {
        super(card);
    }

    @Override
    public GroundSeal copy() {
        return new GroundSeal(this);
    }
}
