package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.CardsLeftGraveyardWatcher;
import mage.abilities.condition.common.CardLeftYourGraveyardThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RelicRetriever extends CardImpl {

    public RelicRetriever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each end step, if a card left your graveyard this turn, create a Treasure token.
        this.addAbility(
            new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY,
                new CreateTokenEffect(new TreasureToken()),
                false,
                CardLeftYourGraveyardThisTurnCondition.instance
            ),
            new CardsLeftGraveyardWatcher()
        );
    }

    private RelicRetriever(final RelicRetriever card) {
        super(card);
    }

    @Override
    public RelicRetriever copy() {
        return new RelicRetriever(this);
    }
}
