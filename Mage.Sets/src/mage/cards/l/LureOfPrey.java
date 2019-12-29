package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LureOfPrey extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a green creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public LureOfPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{G}");

        // Cast Lure of Prey only if an opponent cast a creature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new LureOfPreyRestrictionEffect()));

        // You may put a green creature card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(filter));
    }

    public LureOfPrey(final LureOfPrey card) {
        super(card);
    }

    @Override
    public LureOfPrey copy() {
        return new LureOfPrey(this);
    }
}

class LureOfPreyRestrictionEffect extends ContinuousRuleModifyingEffectImpl {

    public LureOfPreyRestrictionEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast this spell only if an opponent cast a creature spell this turn";
    }

    public LureOfPreyRestrictionEffect(final LureOfPreyRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null) {
                for (UUID playerId : game.getOpponents(source.getControllerId())) {
                    if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(playerId) != 0) {
                        return false; // allow to cast
                    }
                }
            }
            return true; // restrict
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LureOfPreyRestrictionEffect copy() {
        return new LureOfPreyRestrictionEffect(this);
    }
}
