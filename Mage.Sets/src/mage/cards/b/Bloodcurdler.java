package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ExileCardFromOwnGraveyardControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author cbt33, plopman (Immortal Coil)
 */
public final class Bloodcurdler extends CardImpl {

    public Bloodcurdler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put the top card of your library into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new MillCardsControllerEffect(1), TargetController.YOU, false
        ));

        // Threshold - As long as seven or more cards are in your graveyard, Bloodcurdler gets +1/+1 and has "At the beginning of your end step, exile two cards from your graveyard."
        Ability thresholdAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "If seven or more cards are in your graveyard, {this} gets +1/+1"
        ));
        thresholdAbility.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new BeginningOfEndStepTriggeredAbility(
                        new ExileCardFromOwnGraveyardControllerEffect(2), TargetController.YOU, false
                )), ThresholdCondition.instance,
                "and has \"At the beginning of your end step, exile two cards from your graveyard.\""
        ));
        this.addAbility(thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD));
    }

    private Bloodcurdler(final Bloodcurdler card) {
        super(card);
    }

    @Override
    public Bloodcurdler copy() {
        return new Bloodcurdler(this);
    }
}
