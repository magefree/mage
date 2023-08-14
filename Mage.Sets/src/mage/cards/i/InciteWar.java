package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author fireshoes
 */
public final class InciteWar extends CardImpl {

    public InciteWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose one - Creatures target player controls attack this turn if able;
        this.getSpellAbility().addEffect(new InciteWarMustAttackEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());

        // or creatures you control gain first strike until end of turn.
        Mode mode = new Mode(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private InciteWar(final InciteWar card) {
        super(card);
    }

    @Override
    public InciteWar copy() {
        return new InciteWar(this);
    }
}

class InciteWarMustAttackEffect extends OneShotEffect {

    public InciteWarMustAttackEffect() {
        super(Outcome.Detriment);
        staticText = "Creatures target player controls attack this turn if able";
    }

    public InciteWarMustAttackEffect(final InciteWarMustAttackEffect effect) {
        super(effect);
    }

    @Override
    public InciteWarMustAttackEffect copy() {
        return new InciteWarMustAttackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            RequirementEffect effect = new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
