package mage.cards.i;

import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfestationExpert extends TransformingDoubleFacedCard {

    public InfestationExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{G}",
                "Infested Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Infestation Expert
        this.getLeftHalfCard().setPT(3, 4);

        // Whenever Infestation Expert enters the battlefield or attacks, create a 1/1 green Insect creature token.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new InsectToken())
        ));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Infested Werewolf
        this.getRightHalfCard().setPT(4, 5);

        // Whenever Infested Werewolf enters the battlefield or attacks, create two 1/1 green Insect creature token.
        this.getRightHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new InsectToken(), 2)
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private InfestationExpert(final InfestationExpert card) {
        super(card);
    }

    @Override
    public InfestationExpert copy() {
        return new InfestationExpert(this);
    }
}
