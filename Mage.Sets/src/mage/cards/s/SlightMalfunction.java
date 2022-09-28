package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlightMalfunction extends CardImpl {

    public SlightMalfunction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Choose one --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Roll a six-side die. When you do, Slight Malfunction deals 1 damage to each of up to X target creatures, where X is the result.
        this.getSpellAbility().addMode(new Mode(new SlightMalfunctionEffect()));
    }

    private SlightMalfunction(final SlightMalfunction card) {
        super(card);
    }

    @Override
    public SlightMalfunction copy() {
        return new SlightMalfunction(this);
    }
}

class SlightMalfunctionEffect extends OneShotEffect {

    SlightMalfunctionEffect() {
        super(Outcome.Benefit);
        staticText = "roll a six-side die. When you do, {this} deals 1 damage " +
                "to each of up to X target creatures, where X is the result";
    }

    private SlightMalfunctionEffect(final SlightMalfunctionEffect effect) {
        super(effect);
    }

    @Override
    public SlightMalfunctionEffect copy() {
        return new SlightMalfunctionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(1), false);
        ability.addTarget(new TargetAnyTarget(0, result));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
