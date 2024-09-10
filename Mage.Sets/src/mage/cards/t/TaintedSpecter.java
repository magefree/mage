package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class TaintedSpecter extends CardImpl {

    public TaintedSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{B}{B}, {tap}: Target player discards a card unless they put a card from their hand on top of their library.
        // If that player discards a card this way, Tainted Specter deals 1 damage to each creature and each player.
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new TaintedSpecterEffect(), new ManaCostsImpl<>("{1}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private TaintedSpecter(final TaintedSpecter card) {
        super(card);
    }

    @Override
    public TaintedSpecter copy() {
        return new TaintedSpecter(this);
    }
}

class TaintedSpecterEffect extends OneShotEffect {

    TaintedSpecterEffect() {
        super(Outcome.Detriment);
        staticText = "Target player discards a card unless they put a card from their hand on top of their library. "
                + "If that player discards a card this way, {this} deals 1 damage to each creature and each player";
    }

    private TaintedSpecterEffect(final TaintedSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        if (targetPlayer.getHand().isEmpty()) {
            return true;
        }
        final String message = "Discard a card, or put a card from your hand on top of your library?";
        if (targetPlayer.chooseUse(outcome, message, null, "Discard a card", "Put a card from your hand on top", source, game)) {
            // Discard a card
            Effect discardEffect = new DiscardTargetEffect(1);
            discardEffect.setTargetPointer(new FixedTarget(targetPlayer.getId()));

            if (discardEffect.apply(game, source)) {
                new DamageEverythingEffect(1).apply(game, source);
            }
        } else {
            // Put a card from your hand on top of your library
            TargetCardInHand target = new TargetCardInHand().withChooseHint("to put on top of your library");
            targetPlayer.choose(Outcome.Detriment, target, source, game);
            Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                targetPlayer.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
            }
        }

        return true;
    }

    @Override
    public TaintedSpecterEffect copy() {
        return new TaintedSpecterEffect(this);
    }
}
