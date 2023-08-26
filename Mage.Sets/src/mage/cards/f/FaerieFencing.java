package mage.cards.f;

import java.util.HashMap;
import java.util.UUID;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author Xanderhall
 */
public final class FaerieFencing extends CardImpl {

    public FaerieFencing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");
        
        // Target creature gets -X/-X until end of turn. It gets an additional -3/-3 if you controlled a Faerie as you cast this spell.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new FaerieFencingEffect());
        this.getSpellAbility().addWatcher(new ControlledFaerieAsSpellCastWatcher());
    }

    private FaerieFencing(final FaerieFencing card) {
        super(card);
    }

    @Override
    public FaerieFencing copy() {
        return new FaerieFencing(this);
    }
}

class FaerieFencingEffect extends OneShotEffect {

    FaerieFencingEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "target creature gets -X/-X until end of turn. " +
                "That creature gets an additional -3/-3 until end of turn if you controlled a Faerie as you cast this spell.";
    }

    private FaerieFencingEffect(final FaerieFencingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ControlledFaerieAsSpellCastWatcher watcher = game.getState().getWatcher(ControlledFaerieAsSpellCastWatcher.class);
        MageObject mo = source.getSourceObject(game);
        int reduction = source.getManaCostsToPay().getX();

        if (watcher != null && mo != null && watcher.getCount(new MageObjectReference(mo, game)) > 0) {
            reduction += 3;
        }
        game.addEffect(new BoostTargetEffect(-reduction, -reduction, Duration.EndOfTurn), source);
        return true;
    }

    @Override
    public FaerieFencingEffect copy() {
        return new FaerieFencingEffect(this);
    }

}

class ControlledFaerieAsSpellCastWatcher extends Watcher {

    private final FilterPermanent filter = new FilterPermanent(SubType.FAERIE, "");

    ControlledFaerieAsSpellCastWatcher() {
        super(WatcherScope.GAME);
    }

    private final HashMap<MageObjectReference, Integer> permanentsWhenCast = new HashMap<>();

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null) {
                MageObjectReference mor = new MageObjectReference(spell, game);
                permanentsWhenCast.put(mor, game.getBattlefield().countAll(filter, spell.getControllerId(), game));
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        permanentsWhenCast.clear();
    }

    public int getCount(MageObjectReference mor) {
        return permanentsWhenCast.getOrDefault(mor, 0);
    }
}
