package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StringOfDisappearances extends CardImpl {

    public StringOfDisappearances(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Return target creature to its owner's hand. Then that creature's controller may pay {U}{U}. If the player does, they may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new StringOfDisappearancesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StringOfDisappearances(final StringOfDisappearances card) {
        super(card);
    }

    @Override
    public StringOfDisappearances copy() {
        return new StringOfDisappearances(this);
    }
}

class StringOfDisappearancesEffect extends OneShotEffect {

    StringOfDisappearancesEffect() {
        super(Outcome.Damage);
        this.staticText = "Return target creature to its owner's hand. " +
                "Then that creature's controller may pay {U}{U}. " +
                "If the player does, they may copy this spell " +
                "and may choose a new target for that copy.";
    }

    private StringOfDisappearancesEffect(final StringOfDisappearancesEffect effect) {
        super(effect);
    }

    @Override
    public StringOfDisappearancesEffect copy() {
        return new StringOfDisappearancesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player affectedPlayer = game.getPlayer(permanent.getControllerId());
        new ReturnToHandTargetEffect().apply(game, source);
        if (affectedPlayer == null) {
            return false;
        }
        if (!affectedPlayer.chooseUse(Outcome.Copy, "Pay {U}{U} to copy the spell?", source, game)) {
            return true;
        }
        Cost cost = new ManaCostsImpl<>("{U}{U}");
        if (!cost.pay(source, game, source, affectedPlayer.getId(), false, null)) {
            return true;
        }
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell == null) {
            return true;
        }
        spell.createCopyOnStack(game, source, affectedPlayer.getId(), true);
        return true;
    }
}
