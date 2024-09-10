package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class InfernalSovereign extends CardImpl {

    public InfernalSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // Whenever you play a land or cast a spell, you draw a card and you lose 1 life.
        this.addAbility(new InfernalSovereignTriggeredAbility());
    }

    private InfernalSovereign(final InfernalSovereign card) {
        super(card);
    }

    @Override
    public InfernalSovereign copy() {
        return new InfernalSovereign(this);
    }
}

class InfernalSovereignTriggeredAbility extends TriggeredAbilityImpl {

    public InfernalSovereignTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        this.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        setTriggerPhrase("Whenever you play a land or cast a spell, ");
    }

    private InfernalSovereignTriggeredAbility(final InfernalSovereignTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InfernalSovereignTriggeredAbility copy() {
        return new InfernalSovereignTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }
}
