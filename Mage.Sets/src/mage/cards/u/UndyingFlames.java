package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class UndyingFlames extends CardImpl {

    public UndyingFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Exile cards from the top of your library until you exile a nonland card. Undying Flames deals damage to any target equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new UndyingFlamesEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());

    }

    private UndyingFlames(final UndyingFlames card) {
        super(card);
    }

    @Override
    public UndyingFlames copy() {
        return new UndyingFlames(this);
    }
}

class UndyingFlamesEffect extends OneShotEffect {

    public UndyingFlamesEffect() {
        super(Outcome.Benefit);
        staticText = "Exile cards from the top of your library until you exile a nonland card. {this} deals damage to any target equal to that card's mana value";
    }

    private UndyingFlamesEffect(final UndyingFlamesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            while (controller.canRespond() && controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    controller.moveCards(card, Zone.EXILED, source, game);
                    if (!card.isLand(game)) {
                        new DamageTargetEffect(card.getManaValue()).apply(game, source);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public UndyingFlamesEffect copy() {
        return new UndyingFlamesEffect(this);
    }
}
