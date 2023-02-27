package mage.cards.s;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author @stwalsh4118
 */
public final class StaffOfTheStoryteller extends CardImpl {

    public StaffOfTheStoryteller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        

        // When Staff of the Storyteller enters the battlefield, create a 1/1 white Spirit creature token with flying.
        Token token = new SpiritToken();
        token.getColor().addColor(new ObjectColor("W"));
        token.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new CreateTokenEffect(token).setText("create a 1/1 white Spirit creature token with flying."), false));

        // Whenever you create one or more creature tokens, put a story counter on Staff of the Storyteller.
        this.addAbility(new StaffOfTheStorytellerTriggeredAbility());
        // {W}, {T}, Remove a story counter from Staff of the Storyteller: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{W}"));
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

class StaffOfTheStorytellerTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    public StaffOfTheStorytellerTriggeredAbility() {
        super(null, false);
    }

    public StaffOfTheStorytellerTriggeredAbility(final StaffOfTheStorytellerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StaffOfTheStorytellerTriggeredAbility copy() {
        return new StaffOfTheStorytellerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATED_TOKEN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature()) {
            this.getEffects().clear();
            this.addEffect(new AddCountersSourceEffect(CounterType.STORY.createInstance()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you create one or more creature tokens, put a story counter on {this}.";
    }
}
