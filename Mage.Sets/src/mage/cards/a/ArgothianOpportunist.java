package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArgothianOpportunist extends CardImpl {

    public ArgothianOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Argothian Opportunist enters the battlefield, create a tapped Powerstone token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true)
        ));
    }

    private ArgothianOpportunist(final ArgothianOpportunist card) {
        super(card);
    }

    @Override
    public ArgothianOpportunist copy() {
        return new ArgothianOpportunist(this);
    }
}
