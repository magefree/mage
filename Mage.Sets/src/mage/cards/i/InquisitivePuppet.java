package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InquisitivePuppet extends CardImpl {

    public InquisitivePuppet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // When Inquisitive Puppet enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));

        // Exile Inquisitive Puppet: Create a 1/1 white Human creature token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new HumanToken()), new ExileSourceCost()));
    }

    private InquisitivePuppet(final InquisitivePuppet card) {
        super(card);
    }

    @Override
    public InquisitivePuppet copy() {
        return new InquisitivePuppet(this);
    }
}
