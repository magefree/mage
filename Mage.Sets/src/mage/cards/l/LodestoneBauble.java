
package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author ThomasLerner, LevelX2 & L_J
 */
public final class LodestoneBauble extends CardImpl {

    public LodestoneBauble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {1}, {T}, Sacrifice Lodestone Bauble: Put up to four target basic land cards from a player's graveyard on top of their library in any order. That player draws a card at the beginning of the next turn's upkeep.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LodestoneBaubleEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new LodestoneBaubleDrawEffect());
        ability.addTarget(new TargetPlayer(1, 1, true));
        ability.addTarget(new LodestoneBaubleTarget());
        this.addAbility(ability);
    }

    private LodestoneBauble(final LodestoneBauble card) {
        super(card);
    }

    @Override
    public LodestoneBauble copy() {
        return new LodestoneBauble(this);
    }
}

class LodestoneBaubleTarget extends TargetCardInGraveyard {

    public LodestoneBaubleTarget() {
        super(0, 4, new FilterBasicLandCard("basic land cards from a player's graveyard"));
    }

    private LodestoneBaubleTarget(final LodestoneBaubleTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            UUID firstTarget = source.getFirstTarget();
            if (firstTarget != null && game.getPlayer(firstTarget).getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public LodestoneBaubleTarget copy() {
        return new LodestoneBaubleTarget(this);
    }
}

class LodestoneBaubleEffect extends OneShotEffect {
    
    LodestoneBaubleEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put up to four target basic land cards from a player's graveyard on top of their library in any order";
    }

    private LodestoneBaubleEffect(final LodestoneBaubleEffect effect) {
        super(effect);
    }

    @Override
    public LodestoneBaubleEffect copy() {
        return new LodestoneBaubleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> targets = source.getTargets().get(1).getTargets();
            if (targets != null && targets.size() > 0) {
                Cards cards = new CardsImpl(targets);
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}

class LodestoneBaubleDrawEffect extends OneShotEffect {

    public LodestoneBaubleDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "That player draws a card at the beginning of the next turn's upkeep";
    }

    private LodestoneBaubleDrawEffect(final LodestoneBaubleDrawEffect effect) {
        super(effect);
    }

    @Override
    public LodestoneBaubleDrawEffect copy() {
        return new LodestoneBaubleDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = this.getTargetPointer().getFirst(game, source);
        Player targetPlayer = game.getPlayer(targetId);
        if (targetPlayer != null) {
            Effect effect = new DrawCardTargetEffect(StaticValue.get(1), false);
            effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
            DelayedTriggeredAbility ability = new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(ability, source);
            return true;
        }
        return false;
    }

}
