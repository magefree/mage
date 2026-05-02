package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author muz
 */
public final class VisionSynthezoidAvenger extends CardImpl {

    public VisionSynthezoidAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player casts a spell, if it isn't that player's turn, choose one --
        // * Put a +1/+1 counter on Vision.
        // * Vision phases out.
        this.addAbility(new VisionSynthezoidAvengerTriggeredAbility());
    }

    private VisionSynthezoidAvenger(final VisionSynthezoidAvenger card) {
        super(card);
    }

    @Override
    public VisionSynthezoidAvenger copy() {
        return new VisionSynthezoidAvenger(this);
    }
}

class VisionSynthezoidAvengerTriggeredAbility extends SpellCastAllTriggeredAbility {

    VisionSynthezoidAvengerTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        this.setTriggerPhrase("Whenever a player casts a spell, if it isn't that player's turn, ");
        this.addMode(new Mode(new PhaseOutSourceEffect()));
    }

    private VisionSynthezoidAvengerTriggeredAbility(final VisionSynthezoidAvengerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VisionSynthezoidAvengerTriggeredAbility copy() {
        return new VisionSynthezoidAvengerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.isActivePlayer(event.getPlayerId()) && super.checkTrigger(event, game);
    }
}
