package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class MRessCrewCoordinator extends CardImpl {

    public MRessCrewCoordinator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAITIAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land you control enters, create two 1/1 red Officer creature tokens.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new OfficerToken(), 2)));
    }

    private MRessCrewCoordinator(final MRessCrewCoordinator card) {
        super(card);
    }

    @Override
    public MRessCrewCoordinator copy() {
        return new MRessCrewCoordinator(this);
    }
}
