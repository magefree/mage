package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfestationExpert extends CardImpl {

    public InfestationExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.i.InfestedWerewolf.class;

        // Whenever Infestation Expert enters the battlefield or attacks, create a 1/1 green Insect creature token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new InsectToken())
        ));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private InfestationExpert(final InfestationExpert card) {
        super(card);
    }

    @Override
    public InfestationExpert copy() {
        return new InfestationExpert(this);
    }
}
