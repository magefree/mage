package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.KickerWithAnyNumberModesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.InscriptionOfInsightToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InscriptionOfInsight extends CardImpl {

    public InscriptionOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Kicker {2}{U}{U}
        this.addAbility(new KickerWithAnyNumberModesAbility("{2}{U}{U}"));

        // Choose one. If this spell was kicked, choose any number instead.
        // • Return up to two target creatures to their owners' hands.
        this.getSpellAbility().getModes().setChooseText("choose one. If this spell was kicked, choose any number instead.");
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // • Scry 2, then draw two cards.
        Mode mode = new Mode(new ScryEffect(2));
        mode.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.getSpellAbility().addMode(mode);

        // • Target player creates an X/X blue Illusion creature token, where X is the number of cards in their hand.
        mode = new Mode(new InscriptionOfInsightEffect());
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private InscriptionOfInsight(final InscriptionOfInsight card) {
        super(card);
    }

    @Override
    public InscriptionOfInsight copy() {
        return new InscriptionOfInsight(this);
    }
}

class InscriptionOfInsightEffect extends OneShotEffect {

    InscriptionOfInsightEffect() {
        super(Outcome.Benefit);
        staticText = "Target player creates an X/X blue Illusion creature token, " +
                "where X is the number of cards in their hand.";
    }

    private InscriptionOfInsightEffect(final InscriptionOfInsightEffect effect) {
        super(effect);
    }

    @Override
    public InscriptionOfInsightEffect copy() {
        return new InscriptionOfInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        int cardsInHand = player.getHand().size();
        return new InscriptionOfInsightToken(player.getHand().size()).putOntoBattlefield(
                1, game, source.getSourceId(), source.getFirstTarget()
        );
    }
}
