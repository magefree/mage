package mage.cards.j;

import java.util.UUID;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class JeweledBird extends CardImpl {

    public JeweledBird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        

        // Remove Jeweled Bird from your deck before playing if you're not playing for ante.
        // {T}: Put Jeweled Bird into the ante. If you do, put all other cards you own from the ante into your graveyard, then draw a card.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Jeweled Bird from your deck before playing if " +
                "you're not playing for ante.\n{T}: Put Jeweled Bird into the ante. If you do, put all other cards " +
                "you own from the ante into your graveyard, then draw a card."));
    }

    public JeweledBird(final JeweledBird card) {
        super(card);
    }

    @Override
    public JeweledBird copy() {
        return new JeweledBird(this);
    }
}
