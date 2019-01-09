package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BenthicBiomancer extends CardImpl {

    public BenthicBiomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{1}{U}"));

        // Whenever one or more +1/+1 counters are put on Benthic Biomancer, draw a card, then discard a card.
        this.addAbility(new GrowthChamberGuardianTriggeredAbility());
    }

    private BenthicBiomancer(final BenthicBiomancer card) {
        super(card);
    }

    @Override
    public BenthicBiomancer copy() {
        return new BenthicBiomancer(this);
    }
}

class GrowthChamberGuardianTriggeredAbility extends TriggeredAbilityImpl {

    GrowthChamberGuardianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
        this.addEffect(new DiscardControllerEffect(1));
    }

    private GrowthChamberGuardianTriggeredAbility(final GrowthChamberGuardianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrowthChamberGuardianTriggeredAbility copy() {
        return new GrowthChamberGuardianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.P1P1.getName())
                && event.getAmount() > 0
                && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on {this}, " +
                "draw a card, then discard a card.";
    }
}
