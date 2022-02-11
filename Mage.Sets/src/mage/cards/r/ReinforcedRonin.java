package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReinforcedRonin extends CardImpl {

    public ReinforcedRonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of your end step, return Reinforced Ronin to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ReturnToHandSourceEffect(), TargetController.YOU, false
        ));

        // Channel — {1}{R}, Discard Reinforced Ronin: Draw a card.
        this.addAbility(new ChannelAbility("{1}{R}", new DrawCardSourceControllerEffect(1)));
    }

    private ReinforcedRonin(final ReinforcedRonin card) {
        super(card);
    }

    @Override
    public ReinforcedRonin copy() {
        return new ReinforcedRonin(this);
    }
}
