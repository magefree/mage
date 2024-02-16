package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BatToken;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SanguineEvangelist extends CardImpl {

    public SanguineEvangelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // When Sanguine Evangelist enters the battlefield or dies, create a 1/1 black Bat creature token with flying.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new BatToken()), false));

    }

    private SanguineEvangelist(final SanguineEvangelist card) {
        super(card);
    }

    @Override
    public SanguineEvangelist copy() {
        return new SanguineEvangelist(this);
    }
}
