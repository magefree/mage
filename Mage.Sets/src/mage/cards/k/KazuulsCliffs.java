package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KazuulsCliffs extends CardImpl {

    public KazuulsCliffs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Kazuul's Cliffs enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private KazuulsCliffs(final KazuulsCliffs card) {
        super(card);
    }

    @Override
    public KazuulsCliffs copy() {
        return new KazuulsCliffs(this);
    }
}
