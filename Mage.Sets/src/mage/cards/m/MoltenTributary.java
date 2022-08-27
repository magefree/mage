package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenTributary extends CardImpl {

    public MoltenTributary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {U} or {R}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // Molten Tributary enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private MoltenTributary(final MoltenTributary card) {
        super(card);
    }

    @Override
    public MoltenTributary copy() {
        return new MoltenTributary(this);
    }
}
