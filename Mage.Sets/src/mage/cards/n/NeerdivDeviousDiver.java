package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetPlayer;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeerdivDeviousDiver extends CardImpl {

    public NeerdivDeviousDiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Neerdiv, Devious Diver becomes tapped, target player mills cards equal to its power.
        Ability ability = new BecomesTappedSourceTriggeredAbility(
                new MillCardsTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                        .setText("target player mills cards equal to its power")
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever you cast a spell from your graveyard or activate an ability of a card in your graveyard, draw a card and put a +1/+1 counter on Neerdiv.
        this.addAbility(new NeerdivDeviousDiverTriggeredAbility());
    }

    private NeerdivDeviousDiver(final NeerdivDeviousDiver card) {
        super(card);
    }

    @Override
    public NeerdivDeviousDiver copy() {
        return new NeerdivDeviousDiver(this);
    }
}

class NeerdivDeviousDiverTriggeredAbility extends TriggeredAbilityImpl {

    NeerdivDeviousDiverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.setTriggerPhrase("Whenever you cast a spell from your graveyard or activate an ability of a card in your graveyard, ");
    }

    private NeerdivDeviousDiverTriggeredAbility(final NeerdivDeviousDiverTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NeerdivDeviousDiverTriggeredAbility copy() {
        return new NeerdivDeviousDiverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case ACTIVATED_ABILITY:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            case SPELL_CAST:
                return Optional
                        .ofNullable(event.getTargetId())
                        .map(game::getSpell)
                        .map(Spell::getFromZone)
                        .map(Zone.GRAVEYARD::match)
                        .orElse(false);
            case ACTIVATED_ABILITY:
                return Optional
                        .ofNullable(event.getTargetId())
                        .map(game.getStack()::getStackObject)
                        .map(StackObject::getStackAbility)
                        .map(Ability::getSourceId)
                        .filter(uuid -> Zone.GRAVEYARD.match(game.getState().getZone(uuid)))
                        .map(game::getOwnerId)
                        .map(this::isControlledBy)
                        .orElse(false);
        }
        return false;
    }
}
