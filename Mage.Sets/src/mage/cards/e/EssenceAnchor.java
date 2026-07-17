package mage.cards.e;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.CardLeftYourGraveyardThisTurnCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieDruidToken;
import mage.watchers.common.CardsLeftGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EssenceAnchor extends CardImpl {

    public EssenceAnchor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // At the beginning of your upkeep, surveil 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(1)));

        // {T}: Create a 2/2 black Zombie Druid creature token. Activate only during your turn and only if a card left your graveyard this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new CreateTokenEffect(new ZombieDruidToken()),
                new TapSourceCost(), new CompoundCondition(
                        "during your turn and only if a card left your graveyard this turn",
                        MyTurnCondition.instance, CardLeftYourGraveyardThisTurnCondition.instance)
        ), new CardsLeftGraveyardWatcher());
    }

    private EssenceAnchor(final EssenceAnchor card) {
        super(card);
    }

    @Override
    public EssenceAnchor copy() {
        return new EssenceAnchor(this);
    }
}
