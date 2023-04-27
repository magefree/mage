package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author weirddan455
 */
public final class GripOfAmnesia extends CardImpl {

    public GripOfAmnesia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller exiles all cards from their graveyard.
        // Draw a card.
        this.getSpellAbility().addEffect(new GripOfAmnesiaEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private GripOfAmnesia(final GripOfAmnesia card) {
        super(card);
    }

    @Override
    public GripOfAmnesia copy() {
        return new GripOfAmnesia(this);
    }
}

class GripOfAmnesiaEffect extends OneShotEffect {

    public GripOfAmnesiaEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell unless its controller exiles all cards from their graveyard";
    }

    private GripOfAmnesiaEffect(final GripOfAmnesiaEffect effect) {
        super(effect);
    }

    @Override
    public GripOfAmnesiaEffect copy() {
        return new GripOfAmnesiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null && controller.chooseUse(Outcome.Benefit, "Exile all cards from your graveyard? (Otherwise "
                    + spell.getLogName() + " will be countered)", source, game)) {
                controller.moveCards(controller.getGraveyard(), Zone.EXILED, source, game);
                game.informPlayers(controller.getLogName() + " exiles all cards from their graveyard to prevent counter effect");
            } else {
                game.getStack().counter(spell.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}
