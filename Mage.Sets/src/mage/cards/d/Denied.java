package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Denied extends CardImpl {

    public Denied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose a card name, then target spell's controller reveals their hand. If a card with the chosen name is revealed this way, counter that spell.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new DeniedEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Denied(final Denied card) {
        super(card);
    }

    @Override
    public Denied copy() {
        return new Denied(this);
    }
}

class DeniedEffect extends OneShotEffect {

    public DeniedEffect() {
        super(Outcome.Detriment);
        staticText = "Choose a card name, then target spell's controller reveals their hand. If a card with the chosen name is revealed this way, counter that spell";
    }

    private DeniedEffect(final DeniedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if (targetSpell == null) {
            return true;
        }
        Player player = game.getPlayer(targetSpell.getControllerId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (player != null && cardName != null) {
            player.revealCards("Denied!", player.getHand(), game, true);
            for (Card card : player.getHand().getCards(game)) {
                if (card != null && CardUtil.haveSameNames(card, cardName, game)) {
                    game.getStack().counter(targetSpell.getId(), source, game);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DeniedEffect copy() {
        return new DeniedEffect(this);
    }
}
