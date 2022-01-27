
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CabalInterrogator extends CardImpl {

    public CabalInterrogator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{B}, {tap}: Target player reveals X cards from their hand and you choose one of them. That player discards that card.
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new CabalInterrogatorEffect(), new ManaCostsImpl("{X}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CabalInterrogator(final CabalInterrogator card) {
        super(card);
    }

    @Override
    public CabalInterrogator copy() {
        return new CabalInterrogator(this);
    }
}

class CabalInterrogatorEffect extends OneShotEffect {

    public CabalInterrogatorEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals X cards from their hand and you choose one of them. That player discards that card";
    }

    public CabalInterrogatorEffect(final CabalInterrogatorEffect effect) {
        super(effect);
    }

    @Override
    public CabalInterrogatorEffect copy() {
        return new CabalInterrogatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }

        int amountToReveal = (ManacostVariableValue.REGULAR).calculate(game, source, this);

        if (amountToReveal < 1) {
            return true;
        }
        Cards revealedCards = new CardsImpl();
        if (targetPlayer.getHand().size() > amountToReveal) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());

            TargetCard target = new TargetCard(amountToReveal, Zone.HAND, new FilterCard());

            if (targetPlayer.choose(Outcome.Discard, cardsInHand, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        revealedCards.add(card);
                    }
                }
            }
        } else {
            revealedCards.addAll(targetPlayer.getHand());
        }

        TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to discard"));

        if (!revealedCards.isEmpty()) {
            targetPlayer.revealCards("Cabal Interrogator", revealedCards, game);
            Card card = null;
            if (revealedCards.size() > 1) {
                controller.choose(Outcome.Discard, revealedCards, targetInHand, game);
                card = revealedCards.get(targetInHand.getFirstTarget(), game);
            } else {
                card = revealedCards.getRandom(game);
            }

            targetPlayer.discard(card, false, source, game);
        }
        return true;
    }
}
