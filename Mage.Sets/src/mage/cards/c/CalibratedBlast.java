package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CalibratedBlast extends CardImpl {

    public CalibratedBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Reveal cards from the top of your library until you reveal a nonland card. Put the revealed cards on the bottom of your library in a random order. When you reveal a nonland card this way, Calibrated Blast deals damage equal to that card's mana value to any target.
        this.getSpellAbility().addEffect(new CalibratedBlastEffect());

        // Flashback {3}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private CalibratedBlast(final CalibratedBlast card) {
        super(card);
    }

    @Override
    public CalibratedBlast copy() {
        return new CalibratedBlast(this);
    }
}

class CalibratedBlastEffect extends OneShotEffect {

    CalibratedBlastEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a nonland card. " +
                "Put the revealed cards on the bottom of your library in a random order. When you reveal " +
                "a nonland card this way, {this} deals damage equal to that card's mana value to any target";
    }

    private CalibratedBlastEffect(final CalibratedBlastEffect effect) {
        super(effect);
    }

    @Override
    public CalibratedBlastEffect copy() {
        return new CalibratedBlastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isLand(game)) {
                continue;
            }
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new DamageTargetEffect(card.getManaValue()), false, "When you reveal " +
                    "a nonland card this way, {this} deals damage equal to that card's mana value to any target"
            );
            ability.addTarget(new TargetAnyTarget());
            game.fireReflexiveTriggeredAbility(ability, source);
            break;
        }
        player.revealCards(source, cards, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
