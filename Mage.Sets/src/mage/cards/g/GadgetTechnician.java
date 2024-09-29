package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrTurnedFaceUpTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GadgetTechnician extends CardImpl {

    public GadgetTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Gadget Technician enters the battlefield or is turned face up, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));

        // Disguise {U/R}{U/R}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{U/R}{U/R}")));
    }

    private GadgetTechnician(final GadgetTechnician card) {
        super(card);
    }

    @Override
    public GadgetTechnician copy() {
        return new GadgetTechnician(this);
    }
}
