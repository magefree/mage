package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.game.permanent.token.BlackAndRedGoblinToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ElderAuntie extends CardImpl {

    public ElderAuntie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, create a 1/1 black and red Goblin creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BlackAndRedGoblinToken())));
    }

    private ElderAuntie(final ElderAuntie card) {
        super(card);
    }

    @Override
    public ElderAuntie copy() {
        return new ElderAuntie(this);
    }
}
