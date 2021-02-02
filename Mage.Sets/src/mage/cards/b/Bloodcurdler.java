
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.ExileCardFromOwnGraveyardControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author cbt33, plopman (Immortal Coil)
 */
public final class Bloodcurdler extends CardImpl {

    public Bloodcurdler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put the top card of your library into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MillCardsControllerEffect(1), TargetController.YOU, false));

        Condition thresholdCondition = new CardsInControllerGraveyardCondition(7);
        // Threshold - As long as seven or more cards are in your graveyard, Bloodcurdler gets +1/+1 and has "At the beginning of your end step, exile two cards from your graveyard."
        Ability thresholdAbility = new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), thresholdCondition,
            "If seven or more cards are in your graveyard, {this} gets +1/+1"));
        ContinuousEffect effect = new GainAbilitySourceEffect(new BeginningOfEndStepTriggeredAbility(new ExileCardFromOwnGraveyardControllerEffect(2), TargetController.YOU, false));
        thresholdAbility.addEffect(new ConditionalContinuousEffect(effect, thresholdCondition,
            "and has \"At the beginning of your end step, exile two cards from your graveyard.\""));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private Bloodcurdler(final Bloodcurdler card) {
        super(card);
    }

    @Override
    public Bloodcurdler copy() {
        return new Bloodcurdler(this);
    }
}
