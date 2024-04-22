package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TundraFumarole extends CardImpl {

    public TundraFumarole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        this.supertype.add(SuperType.SNOW);

        // Tundra Fumarole deals 4 damage to target creature or planeswalker. Add {C} for each {S} spent to cast this spell. Until end of turn, you don't lose this mana as steps and phases end.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new TundraFumaroleEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private TundraFumarole(final TundraFumarole card) {
        super(card);
    }

    @Override
    public TundraFumarole copy() {
        return new TundraFumarole(this);
    }
}

class TundraFumaroleEffect extends OneShotEffect {

    TundraFumaroleEffect() {
        super(Outcome.Benefit);
        staticText = "Add {C} for each {S} spent to cast this spell. " +
                "Until end of turn, you don't lose this mana as steps and phases end.";
    }

    private TundraFumaroleEffect(final TundraFumaroleEffect effect) {
        super(effect);
    }

    @Override
    public TundraFumaroleEffect copy() {
        return new TundraFumaroleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int snow = ManaPaidSourceWatcher.getSnowPaid(source.getId(), game);
        if (snow > 0) {
            player.getManaPool().addMana(new Mana(ManaType.COLORLESS, snow), game, source, true);
        }
        return true;
    }
}
