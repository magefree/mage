
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ReasonBelieve extends SplitCard {

    public ReasonBelieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{U}", "{4}{G}", SpellAbilityType.SPLIT_AFTERMATH);

        // Reason
        // Scry 3.
        getLeftHalfCard().getSpellAbility().addEffect(new ScryEffect(3));

        // Believe
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Look at the top card of your library. You may put it onto the battlefield if it's a creature card. If you don't, put it into your hand.
        getRightHalfCard().getSpellAbility().addEffect(new BelieveEffect());

    }

    private ReasonBelieve(final ReasonBelieve card) {
        super(card);
    }

    @Override
    public ReasonBelieve copy() {
        return new ReasonBelieve(this);
    }
}

class BelieveEffect extends OneShotEffect {

    BelieveEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Look at the top card of your library. You may put it onto the battlefield if it's a creature card. If you don't, put it into your hand";
    }

    private BelieveEffect(final BelieveEffect effect) {
        super(effect);
    }

    @Override
    public BelieveEffect copy() {
        return new BelieveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                if (card.isCreature(game) && controller.chooseUse(outcome, "Put " + card.getIdName() + " onto the battlefield?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
