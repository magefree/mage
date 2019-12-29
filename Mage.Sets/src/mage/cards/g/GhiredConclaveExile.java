package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.RhinoToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhiredConclaveExile extends CardImpl {

    public GhiredConclaveExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Ghired, Conclave Exile enters the battlefield, create a 4/4 green Rhino creature token with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RhinoToken())));

        // Whenever Ghired attacks, populate. The token enters the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new PopulateEffect(true), false));
    }

    private GhiredConclaveExile(final GhiredConclaveExile card) {
        super(card);
    }

    @Override
    public GhiredConclaveExile copy() {
        return new GhiredConclaveExile(this);
    }
}
