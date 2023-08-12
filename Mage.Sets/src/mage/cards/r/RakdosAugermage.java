package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class RakdosAugermage extends CardImpl {

    public RakdosAugermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {tap}: Reveal your hand and discard a card of target opponent’s choice. Then that player reveals their hand and discards a card of your choice. Activate this ability only any time you could cast a sorcery.
        ActivateAsSorceryActivatedAbility ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new RakdosAugermageEffect(), new TapSourceCost());
        ability.addEffect(new DiscardCardYouChooseTargetEffect().setText(". Then that player reveals their hand and discards a card of your choice"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RakdosAugermage(final RakdosAugermage card) {
        super(card);
    }

    @Override
    public RakdosAugermage copy() {
        return new RakdosAugermage(this);
    }
}

class RakdosAugermageEffect extends OneShotEffect {

    public RakdosAugermageEffect() {
        super(Outcome.Discard);
        staticText = "reveal your hand and discard a card of target opponent's choice";
    }

    public RakdosAugermageEffect(final RakdosAugermageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Cards revealedCards = new CardsImpl();
            revealedCards.addAll(controller.getHand());
            Card sourceCard = game.getCard(source.getSourceId());
            player.revealCards((sourceCard != null ? sourceCard.getIdName() + " (" + sourceCard.getZoneChangeCounter(game) + ") (" : "Discard (") + controller.getName() + ")", revealedCards, game);
            TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
            if (player.choose(Outcome.Benefit, revealedCards, target, source, game)) {
                Card card = revealedCards.get(target.getFirstTarget(), game);
                return player.discard(card, false, source, game);

            }
        }
        return false;
    }

    @Override
    public RakdosAugermageEffect copy() {
        return new RakdosAugermageEffect(this);
    }
}
