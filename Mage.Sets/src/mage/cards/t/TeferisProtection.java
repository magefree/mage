package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.LifeTotalCantChangeControllerEffect;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class TeferisProtection extends CardImpl {

    public TeferisProtection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Until your next turn, your life total can't change, and you have protection from everything. All permanents you control phase out. (While they're phased out, they're treated as though they don't exist. They phase in before you untap during your untap step.)
        this.getSpellAbility().addEffect(new LifeTotalCantChangeControllerEffect(Duration.UntilYourNextTurn)
                .setText("Until your next turn, your life total can't change"));
        this.getSpellAbility().addEffect(new TeferisProtectionEffect());
        this.getSpellAbility().addEffect(new TeferisProtectionPhaseOutEffect());

        // Exile Teferi's Protection.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private TeferisProtection(final TeferisProtection card) {
        super(card);
    }

    @Override
    public TeferisProtection copy() {
        return new TeferisProtection(this);
    }
}

class TeferisProtectionEffect extends OneShotEffect {

    /**
     * 25.08.2017 The following rulings focus on the “protection from” keyword
     * <p>
     * 25.08.2017 If a player has protection from everything, it means three
     * things: 1) All damage that would be dealt to that player is prevented. 2)
     * Auras can't be attached to that player. 3) That player can't be the
     * target of spells or abilities.
     * <p>
     * 25.08.2017 Nothing other than the specified events are prevented or
     * illegal. An effect that doesn't target you could still cause you to
     * discard cards, for example. Creatures can still attack you while you have
     * protection from everything, although combat damage that they would deal
     * to you will be prevented.
     * <p>
     * 25.08.2017 Gaining protection from everything causes a spell or ability
     * on the stack to have an illegal target if it targets you. As a spell or
     * ability tries to resolve, if all its targets are illegal, that spell or
     * ability is countered and none of its effects happen, including effects
     * unrelated to the target. If at least one target is still legal, the spell
     * or ability does as much as it can to the remaining legal targets, and its
     * other effects still happen.
     */
    public TeferisProtectionEffect() {
        super(Outcome.Protect);
        staticText = " and you gain protection from everything";
    }

    public TeferisProtectionEffect(final TeferisProtectionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.addEffect(new GainAbilityControllerEffect(new ProtectionFromEverythingAbility(), Duration.UntilYourNextTurn), source);
            return true;
        }
        return false;
    }

    @Override
    public TeferisProtectionEffect copy() {
        return new TeferisProtectionEffect(this);
    }
}

class TeferisProtectionPhaseOutEffect extends OneShotEffect {

    public TeferisProtectionPhaseOutEffect() {
        super(Outcome.Benefit);
        this.staticText = "All permanents you control phase out. <i>(While they're phased out, they're treated as though they don't exist. They phase in before you untap during your untap step.)</i><br>";
    }

    public TeferisProtectionPhaseOutEffect(final TeferisProtectionPhaseOutEffect effect) {
        super(effect);
    }

    @Override
    public TeferisProtectionPhaseOutEffect copy() {
        return new TeferisProtectionPhaseOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> permIds = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT, controller.getId(), game)) {
                permIds.add(permanent.getId());
            }
            return new PhaseOutAllEffect(permIds).apply(game, source);
        }
        return false;
    }
}
