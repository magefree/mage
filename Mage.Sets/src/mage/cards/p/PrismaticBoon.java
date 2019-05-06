package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismaticBoon extends CardImpl {

    public PrismaticBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{U}");

        // Choose a color. X target creatures gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new PrismaticBoonEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(XTargetsAdjuster.instance);
    }

    private PrismaticBoon(final PrismaticBoon card) {
        super(card);
    }

    @Override
    public PrismaticBoon copy() {
        return new PrismaticBoon(this);
    }
}

class PrismaticBoonEffect extends OneShotEffect {

    PrismaticBoonEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a color. X target creatures gain protection from the chosen color until end of turn.";
    }

    private PrismaticBoonEffect(final PrismaticBoonEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticBoonEffect copy() {
        return new PrismaticBoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                ProtectionAbility.from(choice.getColor()), Duration.EndOfTurn
        ), source);
        return true;
    }
}
