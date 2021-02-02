
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class HeroicIntervention extends CardImpl {

    public HeroicIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Permanents you control gain hexproof and indestructible until end of turn.
        Effect effect = new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, new FilterControlledPermanent(), false);
        effect.setText("permanents you control gain hexproof");
        getSpellAbility().addEffect(effect);
        effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledPermanent(), false);
        effect.setText("and indestructible until end of turn");
        getSpellAbility().addEffect(effect);
    }

    private HeroicIntervention(final HeroicIntervention card) {
        super(card);
    }

    @Override
    public HeroicIntervention copy() {
        return new HeroicIntervention(this);
    }
}
