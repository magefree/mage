package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.KnightToken;
import mage.game.stack.Spell;
import mage.target.common.TargetNonlandPermanent;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BanishIntoFable extends CardImpl {

    public BanishIntoFable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}{U}");

        // When you cast this spell from your hand, copy it if you control an artifact, then copy it if you control an enchantment. You may choose new targets for the copies.
        this.addAbility(new BanishIntoFableTriggeredAbility());

        // Return target nonland permanent to its owner's hand. You create a 2/2 white Knight creature token with vigilance.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken())
                .setText("You create a 2/2 white Knight creature token with vigilance"));
    }

    private BanishIntoFable(final BanishIntoFable card) {
        super(card);
    }

    @Override
    public BanishIntoFable copy() {
        return new BanishIntoFable(this);
    }
}

class BanishIntoFableTriggeredAbility extends CastSourceTriggeredAbility {

    BanishIntoFableTriggeredAbility() {
        super(null, false);
        this.addWatcher(new CastFromHandWatcher());
        this.setRuleAtTheTop(true);
    }

    private BanishIntoFableTriggeredAbility(BanishIntoFableTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        if (watcher == null || !watcher.spellWasCastFromHand(event.getSourceId())) {
            return false;
        }
        Spell spell = game.getState().getStack().getSpell(event.getSourceId());
        if (spell == null) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new BanishIntoFableEffect(spell.getId()));
        return true;
    }

    @Override
    public BanishIntoFableTriggeredAbility copy() {
        return new BanishIntoFableTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast this spell from your hand, copy it if you control an artifact, " +
                "then copy it if you control an enchantment. You may choose new targets for the copies.";
    }
}

class BanishIntoFableEffect extends OneShotEffect {

    private final UUID spellId;

    BanishIntoFableEffect(UUID spellId) {
        super(Outcome.Benefit);
        this.spellId = spellId;
    }

    private BanishIntoFableEffect(final BanishIntoFableEffect effect) {
        super(effect);
        this.spellId = effect.spellId;
    }

    @Override
    public BanishIntoFableEffect copy() {
        return new BanishIntoFableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(spellId);
        if (spell == null) {
            return false;
        }
        if (game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .filter(permanent -> permanent.isArtifact(game))
                .count() > 0) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        if (game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .filter(permanent -> permanent.isEnchantment(game))
                .count() > 0) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        return true;
    }
}
