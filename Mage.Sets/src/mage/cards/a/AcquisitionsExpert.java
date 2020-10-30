package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcquisitionsExpert extends CardImpl {

    public AcquisitionsExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Acquisitions Expert enters the battlefield, target opponent reveals a number of cards from their hand equal to the number of creatures in your party. You choose one of those cards. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardCardYouChooseTargetEffect(TargetController.ANY, PartyCount.instance)
                        .setText("target opponent reveals a number of cards from their hand " +
                                "equal to the number of creatures in your party. You choose one of those cards. " +
                                "That player discards that card. " + PartyCount.getReminder())
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private AcquisitionsExpert(final AcquisitionsExpert card) {
        super(card);
    }

    @Override
    public AcquisitionsExpert copy() {
        return new AcquisitionsExpert(this);
    }
}
