package mage.cards.g;

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
public final class GuadosalamFarplaneGateway extends CardImpl {

    public GuadosalamFarplaneGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private GuadosalamFarplaneGateway(final GuadosalamFarplaneGateway card) {
        super(card);
    }

    @Override
    public GuadosalamFarplaneGateway copy() {
        return new GuadosalamFarplaneGateway(this);
    }
}
