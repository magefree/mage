package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellishRebuke extends CardImpl {

    public HellishRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Until end of turn, permanents your opponents control gain "When this permanent deals damage to the player who cast Hellish Rebuke, sacrifice this permanent. You lose 2 life."
        this.getSpellAbility().addEffect(new HellishRebukeEffect());
        this.getSpellAbility().addWatcher(new SpellsCastWatcher());
    }

    private HellishRebuke(final HellishRebuke card) {
        super(card);
    }

    @Override
    public HellishRebuke copy() {
        return new HellishRebuke(this);
    }
}

class HellishRebukeEffect extends OneShotEffect {

    HellishRebukeEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, permanents your opponents control gain " +
                "\"When this permanent deals damage to the player who cast {this}, " +
                "sacrifice this permanent. You lose 2 life.\"";
    }

    private HellishRebukeEffect(final HellishRebukeEffect effect) {
        super(effect);
    }

    @Override
    public HellishRebukeEffect copy() {
        return new HellishRebukeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = source.getSourceObject(game);
        game.addEffect(new GainAbilityAllEffect(
                new HellishRebukeTriggeredAbility(source, game),
                Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT
        ), source);
        return true;
    }
}

class HellishRebukeTriggeredAbility extends TriggeredAbilityImpl {

    private final String sourceName;
    private final UUID casterId;

    HellishRebukeTriggeredAbility(Ability source, Game game) {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        this.addEffect(new LoseLifeSourceControllerEffect(2));
        this.sourceName = getSourceName(source, game);
        this.casterId = getCasterId(source, game);
    }

    private HellishRebukeTriggeredAbility(final HellishRebukeTriggeredAbility ability) {
        super(ability);
        this.sourceName = ability.sourceName;
        this.casterId = ability.casterId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId()) && event.getPlayerId().equals(casterId);
    }

    @Override
    public HellishRebukeTriggeredAbility copy() {
        return new HellishRebukeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When this permanent deals damage to the player who cast "
                + sourceName + ", sacrifice this permanent. You lose 2 life.";
    }

    private static final String getSourceName(Ability source, Game game) {
        MageObject object = source.getSourceObject(game);
        return object != null ? object.getName() : "Hellish Rebuke";
    }

    private static final UUID getCasterId(Ability source, Game game) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher.getCasterId(source, game);
    }
}
