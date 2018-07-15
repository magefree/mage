package mage.cards.b;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author noahg
 */
public final class BronzeTablet extends CardImpl {

    public BronzeTablet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");


        // Remove Bronze Tablet from your deck before playing if you're not playing for ante.
        // Bronze Tablet enters the battlefield tapped.
        // {4}, {T}: Exile Bronze Tablet and target nontoken permanent an opponent owns. That player may pay 10 life. If they do, put Bronze Tablet into its owner's graveyard. Otherwise, that player owns Bronze Tablet and you own the other exiled card.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Bronze Tablet from your deck before playing if you’re" +
                " not playing for ante.\nBronze Tablet enters the battlefield tapped.\n{4}, {T}: Exile Bronze Tablet " +
                "and target nontoken permanent an opponent owns. That player may pay 10 life. If they do, put Bronze " +
                "Tablet into its owner’s graveyard. Otherwise, that player owns Bronze Tablet and you own the other " +
                "exiled card."));
    }

    public BronzeTablet(final BronzeTablet card) {
        super(card);
    }

    @Override
    public BronzeTablet copy() {
        return new BronzeTablet(this);
    }
}
