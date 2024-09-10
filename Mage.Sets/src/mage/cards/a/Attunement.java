package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Backfir3
 */
public final class Attunement extends CardImpl {

    public Attunement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Return Attunement to its owner's hand: Draw three cards, then discard four cards.
		SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new ReturnToHandFromBattlefieldSourceCost());
		ability.addEffect(new DiscardControllerEffect(4).concatBy(", then"));
        this.addAbility(ability);
    }

    private Attunement(final Attunement card) {
        super(card);
    }

    @Override
    public Attunement copy() {
        return new Attunement(this);
    }
}
