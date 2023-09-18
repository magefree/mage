package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiresOfInvention extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("spells with mana value less than or equal to the number of lands you control");

    static {
        filter.add(FiresOfInventionPredicate.instance);
    }

    public FiresOfInvention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // You can cast spells only during your turn and you can cast no more than two spells each turn.
        this.addAbility(new SimpleStaticAbility(new FiresOfInventionCastEffect()));

        // You may cast spells with converted mana cost less than or equal to the number of lands you control without paying their mana costs.
        this.addAbility(new SimpleStaticAbility(new CastFromHandWithoutPayingManaCostEffect(filter, false)));
    }

    private FiresOfInvention(final FiresOfInvention card) {
        super(card);
    }

    @Override
    public FiresOfInvention copy() {
        return new FiresOfInvention(this);
    }
}

enum FiresOfInventionPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getManaValue() <=
                game.getBattlefield().countAll(StaticFilters.FILTER_LAND, game.getControllerId(input.getSourceId()), game);
    }
}

class FiresOfInventionCastEffect extends ContinuousRuleModifyingEffectImpl {

    FiresOfInventionCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "you can cast spells only during your turn and you can cast no more than two spells each turn";
    }

    private FiresOfInventionCastEffect(final FiresOfInventionCastEffect effect) {
        super(effect);
    }

    @Override
    public FiresOfInventionCastEffect copy() {
        return new FiresOfInventionCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        return watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) > 1
                || !game.getActivePlayerId().equals(source.getControllerId());
    }

}