package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.*;

/**
 * @author: Merlingilb
 */
public final class AJedisFervor extends CardImpl {
    public AJedisFervor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES)
        );

        // If an opponent cast a black spell this turn, that player sacrifices a creature or planeswalker.
        this.getSpellAbility().addEffect(new AJedisFervorEffect());
        this.getSpellAbility().addWatcher(new SpellsCastWatcher());
    }

    private AJedisFervor(final AJedisFervor card) {
        super(card);
    }

    @Override
    public AJedisFervor copy() {
        return new AJedisFervor(this);
    }
}

class AJedisFervorEffect extends OneShotEffect {
    public AJedisFervorEffect() {
        super(Outcome.Sacrifice);
        staticText = "If an opponent cast a black spell this turn, that player sacrifices a creature or planeswalker.";
    }

    public AJedisFervorEffect(final AJedisFervorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        Set<UUID> opponentsBlack = new HashSet<>();
        List<UUID> perms = new ArrayList<>();
        //get opponents that cast a black spell this turn
        if (watcher != null) {
            for (UUID opponentId : opponents) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(opponentId);
                if (spells != null) {
                    for (Spell spell : spells) {
                        if (spell.getColor(game).isBlack()) {
                            opponentsBlack.add(opponentId);
                        }
                    }
                }
            }
        }
        //get that opponents to pick a creature or planeswalker
        for (UUID opponentId : opponentsBlack) {
            TargetPermanent target = new TargetPermanent(1, 1,
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER, false);
            game.getPlayer(opponentId).chooseTarget(Outcome.Sacrifice, target, source, game);
            perms.addAll(target.getTargets());
        }
        //sacrifices the picked cards
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }

    @Override
    public AJedisFervorEffect copy() {
        return new AJedisFervorEffect(this);
    }
}