
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author fireshoes
 */
public final class RishkarsExpertise extends CardImpl {

    public RishkarsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Draw cards equal to the greatest power among creatures you control.
        Effect effect = new DrawCardSourceControllerEffect(new GreatestPowerAmongControlledCreaturesValue());
        effect.setText("Draw cards equal to the greatest power among creatures you control");
        this.getSpellAbility().addEffect(effect);

        // You may cast a card with converted mana cost 5 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new CastWithoutPayingManaCostEffect(5));
    }

    public RishkarsExpertise(final RishkarsExpertise card) {
        super(card);
    }

    @Override
    public RishkarsExpertise copy() {
        return new RishkarsExpertise(this);
    }
}
