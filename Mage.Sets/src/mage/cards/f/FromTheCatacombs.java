package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FromTheCatacombs extends CardImpl {

    public FromTheCatacombs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Return target creature card from a graveyard to the battlefield with a corpse counter on it. If that creature would leave the battlefield, exile it instead of putting it anywhere else.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.CORPSE.createInstance()));
        this.getSpellAbility().addEffect(new FromTheCatacombsEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));

        // You take the initiative.
        this.getSpellAbility().addEffect(new TakeTheInitiativeEffect().concatBy("<br>"));

        // Escape—{3}{B}{B}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{B}{B}", 4));
    }

    private FromTheCatacombs(final FromTheCatacombs card) {
        super(card);
    }

    @Override
    public FromTheCatacombs copy() {
        return new FromTheCatacombs(this);
    }
}

class FromTheCatacombsEffect extends ReplacementEffectImpl {

    FromTheCatacombsEffect() {
        super(Duration.Custom, Outcome.Exile);
        staticText = "If that creature would leave the battlefield, exile it instead of putting it anywhere else";
    }

    private FromTheCatacombsEffect(final FromTheCatacombsEffect effect) {
        super(effect);
    }

    @Override
    public FromTheCatacombsEffect copy() {
        return new FromTheCatacombsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getFirstTarget())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
