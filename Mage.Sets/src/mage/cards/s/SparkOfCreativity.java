
package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SparkOfCreativity extends CardImpl {

    public SparkOfCreativity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Choose target creature. Exile the top card of your library. You may have Spark of Creativity deal damage to that creature equal to the converted mana cost of the exiled card. If you don't, you may play that card until end of turn.
        this.getSpellAbility().addEffect(new SparkOfCreativityEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SparkOfCreativity(final SparkOfCreativity card) {
        super(card);
    }

    @Override
    public SparkOfCreativity copy() {
        return new SparkOfCreativity(this);
    }
}

class SparkOfCreativityEffect extends OneShotEffect {

    public SparkOfCreativityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature. Exile the top card of your library. You may have {this} deal damage to that creature equal to the exiled card's mana value. If you don't, you may play that card until end of turn";
    }

    public SparkOfCreativityEffect(final SparkOfCreativityEffect effect) {
        super(effect);
    }

    @Override
    public SparkOfCreativityEffect copy() {
        return new SparkOfCreativityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                // You may have Spark of Creativity deal damage to that creature equal to the converted mana cost of the exiled card.
                Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    int cmc = card.getManaCost().manaValue();
                    if (controller.chooseUse(outcome, "Let " + sourceObject.getLogName() + " deal " + cmc + " damage to " + targetCreature.getLogName() + '?', source, game)) {
                        targetCreature.damage(cmc, source.getSourceId(), source, game, false, true);
                        return true;
                    }
                }
                // If you don't, you may play that card until end of turn
                CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn);
            }
            return true;
        }
        return false;
    }
}
