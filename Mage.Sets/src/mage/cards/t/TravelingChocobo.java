package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TravelingChocobo extends CardImpl {

    private static final FilterCard filter = new FilterCard("play lands and cast Bird spells");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                SubType.BIRD.getPredicate()
        ));
    }

    public TravelingChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands and cast Bird spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // If a land or Bird you control entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new TravelingChocoboEffect()));
    }

    private TravelingChocobo(final TravelingChocobo card) {
        super(card);
    }

    @Override
    public TravelingChocobo copy() {
        return new TravelingChocobo(this);
    }
}

class TravelingChocoboEffect extends ReplacementEffectImpl {

    TravelingChocoboEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a land or Bird you control entering the battlefield causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private TravelingChocoboEffect(final TravelingChocoboEffect effect) {
        super(effect);
    }

    @Override
    public TravelingChocoboEffect copy() {
        return new TravelingChocoboEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)) {
            return false;
        }
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null
                || sourceEvent.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || !(sourceEvent instanceof EntersTheBattlefieldEvent)) {
            return false;
        }
        EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
        return (entersTheBattlefieldEvent.getTarget().isLand(game)
                || entersTheBattlefieldEvent.getTarget().hasSubtype(SubType.BIRD, game))
                && entersTheBattlefieldEvent.getTarget().isControlledBy(source.getControllerId())
                && game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
