package mage.cards.t;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TravelTheOverworld extends CardImpl {

    public TravelTheOverworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Affinity for Towns
        this.addAbility(new AffinityAbility(AffinityType.TOWNS));

        // Draw four cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
    }

    private TravelTheOverworld(final TravelTheOverworld card) {
        super(card);
    }

    @Override
    public TravelTheOverworld copy() {
        return new TravelTheOverworld(this);
    }
}
