package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.CreatedTokensEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StaffOfTheStoryteller extends CardImpl {

    public StaffOfTheStoryteller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // When Staff of the Storyteller enters the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));

        // Whenever you create one or more creature tokens, put a story counter on Staff of the Storyteller.
        this.addAbility(new StaffOfTheStorytellerTriggeredAbility());

        // {W}, {T}, Remove a story counter from Staff of the Storyteller: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.STORY.createInstance()));
        this.addAbility(ability);
    }

    private StaffOfTheStoryteller(final StaffOfTheStoryteller card) {
        super(card);
    }

    @Override
    public StaffOfTheStoryteller copy() {
        return new StaffOfTheStoryteller(this);
    }
}

class StaffOfTheStorytellerTriggeredAbility extends TriggeredAbilityImpl {

    StaffOfTheStorytellerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORY.createInstance()));
        setTriggerPhrase("Whenever you create one or more creature tokens, ");
    }

    private StaffOfTheStorytellerTriggeredAbility(final StaffOfTheStorytellerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StaffOfTheStorytellerTriggeredAbility copy() {
        return new StaffOfTheStorytellerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATED_TOKENS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && ((CreatedTokensEvent) event)
                .getCreatedTokens()
                .stream()
                .anyMatch(p -> p.isCreature(game));
    }
}
