package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Grath
 */
public final class DrivnodCarnageDominus extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("three creature cards from your graveyard");

    public DrivnodCarnageDominus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(3);

        // If a creature dying causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new DrivnodCarnageDominusEffect()));

        // {B/P}{B/P}, Exile three creature cards from your graveyard: Put an indestructible counter on Drivnod, Carnage Dominus.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()), new ManaCostsImpl<>("{B/P}{B/P}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, filter)));
        this.addAbility(ability);
    }

    private DrivnodCarnageDominus(final DrivnodCarnageDominus card) {
        super(card);
    }

    @Override
    public DrivnodCarnageDominus copy() {
        return new DrivnodCarnageDominus(this);
    }
}

class DrivnodCarnageDominusEffect extends ReplacementEffectImpl {

    DrivnodCarnageDominusEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature dying causes a triggered ability of a permanent you control to trigger, " +
                "that ability triggers an additional time.";
    }

    private DrivnodCarnageDominusEffect(final DrivnodCarnageDominusEffect effect) {
        super(effect);
    }

    @Override
    public DrivnodCarnageDominusEffect copy() {
        return new DrivnodCarnageDominusEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            if (source.isControlledBy(event.getPlayerId())
                    && game.getPermanentOrLKIBattlefield(numberOfTriggersEvent.getSourceId()) != null
                    && numberOfTriggersEvent.getSourceEvent() instanceof ZoneChangeEvent) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) numberOfTriggersEvent.getSourceEvent();
                return zEvent.isDiesEvent()
                        && zEvent.getTarget() != null
                        && zEvent.getTarget().isCreature(game);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
