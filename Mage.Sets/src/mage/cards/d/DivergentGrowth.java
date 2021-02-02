
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author Galatolol
 */
public final class DivergentGrowth extends CardImpl {

    public DivergentGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");
        

        // Until end of turn, lands you control gain "{tap}: Add one mana of any color."
        ActivatedManaAbilityImpl ability = new AnyColorManaAbility();
        Effect effect = new GainAbilityAllEffect(ability, Duration.EndOfTurn, new FilterControlledLandPermanent());
        effect.setText("Until end of turn, lands you control gain \"{T}: Add one mana of any color.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private DivergentGrowth(final DivergentGrowth card) {
        super(card);
    }

    @Override
    public DivergentGrowth copy() {
        return new DivergentGrowth(this);
    }
}
