package mage.cards.r;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BlueBirdToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RikuOfManyPaths extends CardImpl {

    public RikuOfManyPaths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a modal spell, choose up to X, where X is the number of times you chose a mode for that spell --
        // * Exile the top card of your library. Until the end of your next turn, you may play it.
        // * Put a +1/+1 counter on Riku of Many Paths. It gains trample until end of turn.
        // * Create a 1/1 blue Bird creature token with flying.
        this.addAbility(new RikuOfManyPathsTriggeredAbility());
    }

    private RikuOfManyPaths(final RikuOfManyPaths card) {
        super(card);
    }

    @Override
    public RikuOfManyPaths copy() {
        return new RikuOfManyPaths(this);
    }
}

class RikuOfManyPathsTriggeredAbility extends TriggeredAbilityImpl {

    RikuOfManyPathsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
                .withTextOptions("it", false));
        this.setTriggerPhrase("Whenever you cast a modal spell, ");
        this.getModes().setChooseText("choose up to X, where X is the number of times you chose a mode for that spell &mdash;");
        this.addMode(new Mode(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .addEffect(new GainAbilitySourceEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn
                ).setText("it gains trample until end of turn")));
        this.addMode(new Mode(new CreateTokenEffect(new BlueBirdToken())));
        this.getModes().setMinModes(0);
    }

    private RikuOfManyPathsTriggeredAbility(final RikuOfManyPathsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RikuOfManyPathsTriggeredAbility copy() {
        return new RikuOfManyPathsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null
                || !spell.isControlledBy(getControllerId())
                || spell.getSpellAbility().getModes().size() < 2) {
            return false;
        }
        this.getModes().setMaxModes(spell.getSpellAbility().getModes().getSelectedModes().size());
        return true;
    }
}
