
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author markedagain
 */
public final class HauntingMisery extends CardImpl {

    public HauntingMisery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // As an additional cost to cast Haunting Misery, exile X creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileXFromYourGraveCost(new FilterCreatureCard()));
        // Haunting Misery deals X damage to target player.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(new GetXValue()));
    }

    public HauntingMisery(final HauntingMisery card) {
        super(card);
    }

    @Override
    public HauntingMisery copy() {
        return new HauntingMisery(this);
    }
}
