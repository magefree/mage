package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntroductionToAnnihilation extends CardImpl {

    public IntroductionToAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}");

        this.subtype.add(SubType.LESSON);

        // Exile target nonland permanent. Its controller draws a card.
        this.getSpellAbility().addEffect(new IntroductionToAnnihilationEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private IntroductionToAnnihilation(final IntroductionToAnnihilation card) {
        super(card);
    }

    @Override
    public IntroductionToAnnihilation copy() {
        return new IntroductionToAnnihilation(this);
    }
}

class IntroductionToAnnihilationEffect extends OneShotEffect {

    IntroductionToAnnihilationEffect() {
        super(Outcome.Benefit);
        staticText = "exile target nonland permanent. Its controller draws a card";
    }

    private IntroductionToAnnihilationEffect(final IntroductionToAnnihilationEffect effect) {
        super(effect);
    }

    @Override
    public IntroductionToAnnihilationEffect copy() {
        return new IntroductionToAnnihilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
