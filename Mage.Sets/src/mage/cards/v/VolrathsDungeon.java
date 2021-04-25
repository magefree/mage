package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VolrathsDungeon extends CardImpl {

    public VolrathsDungeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Pay 5 life: Destroy Volrath's Dungeon. Any player may activate this ability but only during their turn.
        ActivatedAbility ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DestroySourceEffect().setText("Destroy {this}. Any player may activate this ability but only during their turn."),
                new PayLifeActivePlayerCost(5));
        ability.setMayActivate(TargetController.ACTIVE);
        this.addAbility(ability);

        // Discard a card: Target player puts a card from their hand on top of their library. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new VolrathsDungeonEffect(), new DiscardCardCost()
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VolrathsDungeon(final VolrathsDungeon card) {
        super(card);
    }

    @Override
    public VolrathsDungeon copy() {
        return new VolrathsDungeon(this);
    }
}

class PayLifeActivePlayerCost extends CostImpl {

    private final DynamicValue amount;

    public PayLifeActivePlayerCost(int amount) {
        this.amount = StaticValue.get(amount);
        this.text = "Pay " + amount + " life";
    }

    public PayLifeActivePlayerCost(DynamicValue amount, String text) {
        this.amount = amount.copy();
        this.text = "Pay " + text;
    }

    public PayLifeActivePlayerCost(PayLifeActivePlayerCost cost) {
        super(cost);
        this.amount = cost.amount.copy();
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int lifeToPayAmount = amount.calculate(game, ability, null);
        return game.getPlayer(game.getActivePlayerId()).getLife() >= lifeToPayAmount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player activatingPlayer = game.getPlayer(game.getActivePlayerId());
        if (activatingPlayer == null) {
            return false;
        }

        int lifeToPayAmount = amount.calculate(game, ability, null);
        if (activatingPlayer.chooseUse(Outcome.LoseLife, "Pay " + lifeToPayAmount + " life?", ability, game)) {
            this.paid = CardUtil.tryPayLife(lifeToPayAmount, activatingPlayer, source, game);
            return this.paid;
        }
        return false;
    }

    @Override
    public PayLifeActivePlayerCost copy() {
        return new PayLifeActivePlayerCost(this);
    }
}

class VolrathsDungeonEffect extends OneShotEffect {

    public VolrathsDungeonEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target player puts a card from their hand on top of their library";
    }

    public VolrathsDungeonEffect(final VolrathsDungeonEffect effect) {
        super(effect);
    }

    @Override
    public VolrathsDungeonEffect copy() {
        return new VolrathsDungeonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetedPlayer = game.getPlayer(source.getFirstTarget());
        if (targetedPlayer != null) {
            TargetCardInHand target = new TargetCardInHand();
            if (targetedPlayer.choose(Outcome.Detriment, targetedPlayer.getHand(), target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                // must hides the card name from other players
                return card != null && targetedPlayer.putCardOnTopXOfLibrary(card, game, source, 0, false);
            }
        }
        return false;
    }
}
