package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GongagaReactorTown extends CardImpl {

    public GongagaReactorTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private GongagaReactorTown(final GongagaReactorTown card) {
        super(card);
    }

    @Override
    public GongagaReactorTown copy() {
        return new GongagaReactorTown(this);
    }
}
