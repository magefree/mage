package mage.cards.i;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrencragFeat extends CardImpl {

    public IrencragFeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}{R}");

        // Add seven {R}. You can cast only one more spell this turn.
        this.getSpellAbility().addEffect(new IrencragFeatEffect());
    }

    private IrencragFeat(final IrencragFeat card) {
        super(card);
    }

    @Override
    public IrencragFeat copy() {
        return new IrencragFeat(this);
    }
}

class IrencragFeatEffect extends OneShotEffect {

    private static final Effect effect = new BasicManaEffect(Mana.RedMana(7));

    IrencragFeatEffect() {
        super(Outcome.Benefit);
        staticText = "Add seven {R}. You can cast only one more spell this turn.";
    }

    private IrencragFeatEffect(final IrencragFeatEffect effect) {
        super(effect);
    }

    @Override
    public IrencragFeatEffect copy() {
        return new IrencragFeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        int spellsCast = watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId());
        game.addEffect(new IrencragFeatCantCastEffect(spellsCast), source);
        return effect.apply(game, source);
    }
}

class IrencragFeatCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    private final int spellsCast;

    IrencragFeatCantCastEffect(int spellsCast) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.spellsCast = spellsCast;
    }

    private IrencragFeatCantCastEffect(final IrencragFeatCantCastEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public IrencragFeatCantCastEffect copy() {
        return new IrencragFeatCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return event.getPlayerId().equals(source.getControllerId())
                && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) > spellsCast + 1;
    }
}