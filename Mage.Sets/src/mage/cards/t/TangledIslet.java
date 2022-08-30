package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TangledIslet extends CardImpl {

    public TangledIslet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {G} or {U}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // Tangled Islet enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private TangledIslet(final TangledIslet card) {
        super(card);
    }

    @Override
    public TangledIslet copy() {
        return new TangledIslet(this);
    }
}
