
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class CoaxFromTheBlindEternities extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Eldrazi card");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
    }

    public CoaxFromTheBlindEternities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // You may choose an Eldrazi card you own from outside the game or in exile, reveal that card, and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter, true, true));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private CoaxFromTheBlindEternities(final CoaxFromTheBlindEternities card) {
        super(card);
    }

    @Override
    public CoaxFromTheBlindEternities copy() {
        return new CoaxFromTheBlindEternities(this);
    }
}
