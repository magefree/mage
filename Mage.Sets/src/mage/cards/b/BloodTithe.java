package mage.cards.b;

import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BloodTithe extends CardImpl {

    public BloodTithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Each opponent loses 3 life. You gain life equal to the life lost this way.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsYouGainLifeLostEffect(3));
    }

    private BloodTithe(final BloodTithe card) {
        super(card);
    }

    @Override
    public BloodTithe copy() {
        return new BloodTithe(this);
    }

}
