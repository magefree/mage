package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongMadRuins extends CardImpl {

    public SongMadRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Song-Mad Ruins enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private SongMadRuins(final SongMadRuins card) {
        super(card);
    }

    @Override
    public SongMadRuins copy() {
        return new SongMadRuins(this);
    }
}
