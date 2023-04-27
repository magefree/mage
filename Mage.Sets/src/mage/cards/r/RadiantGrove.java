package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantGrove extends CardImpl {

    public RadiantGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {G} or {W}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Radiant Grove enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private RadiantGrove(final RadiantGrove card) {
        super(card);
    }

    @Override
    public RadiantGrove copy() {
        return new RadiantGrove(this);
    }
}
