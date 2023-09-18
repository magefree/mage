package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfestedWerewolf extends CardImpl {

    public InfestedWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Infested Werewolf enters the battlefield or attacks, create two 1/1 green Insect creature token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new InsectToken(), 2)
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private InfestedWerewolf(final InfestedWerewolf card) {
        super(card);
    }

    @Override
    public InfestedWerewolf copy() {
        return new InfestedWerewolf(this);
    }
}
