package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author rockydirtbag
 */
public final class GandalfTheWhite extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary spells and artifact spells");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SuperType.LEGENDARY.getPredicate()));
    }
    public GandalfTheWhite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may cast legendary spells and artifact spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)
        ));
        // If a legendary permanent or an artifact entering or leaving the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GandalfTheWhiteDoublingEffect()));
    }

    private GandalfTheWhite(final GandalfTheWhite card) {
        super(card);
    }

    @Override
    public GandalfTheWhite copy() {
        return new GandalfTheWhite(this);
    }
}

class GandalfTheWhiteDoublingEffect extends ReplacementEffectImpl {
    GandalfTheWhiteDoublingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a legendary permanent or an artifact entering or leaving the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.";
    }

    GandalfTheWhiteDoublingEffect(final GandalfTheWhiteDoublingEffect effect) {
        super(effect);
    }

    @Override
    public GandalfTheWhiteDoublingEffect copy() {
        return new GandalfTheWhiteDoublingEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        // Only triggers of the controller of Gandalf
        if (source.isControlledBy(event.getPlayerId())) {
            GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
            // 1) EtB triggers
            if (sourceEvent != null
                    && sourceEvent.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                    && sourceEvent instanceof EntersTheBattlefieldEvent) {
                EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
                // Only for entering artifacts or legendaries
                if (entersTheBattlefieldEvent.getTarget().isArtifact(game)
                        || entersTheBattlefieldEvent.getTarget().isLegendary(game)) {
                    // Only for triggers of permanents
                    return game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
                }
            }
            // 2) LtB triggers
            if (sourceEvent != null
                    && sourceEvent.getType() == GameEvent.EventType.ZONE_CHANGE
                    && sourceEvent instanceof ZoneChangeEvent) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) sourceEvent;
                // Only for leaving artifacts or legendaries
                if ((zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() != Zone.BATTLEFIELD)
                        && (zEvent.getTarget().isLegendary(game) || zEvent.getTarget().isArtifact(game))) {
                    // Only for triggers of permanents
                    return game.getPermanentOrLKIBattlefield(numberOfTriggersEvent.getSourceId()) != null;
                }
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
