package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.EarlyTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class KnollspineInvocation extends CardImpl {

    protected static final FilterCard filter = new FilterCard("a card with mana value X");

    public KnollspineInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // {X}, Discard a card with mana value X: This enchantment deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(GetXValue.instance, true), new ManaCostsImpl<>("{X}"));
        ability.addCost(new KnollspineInvocationDiscardCost());
        ability.addTarget(new TargetAnyTarget());
        ability.setCostAdjuster(KnollspineInvocationAdjuster.instance);
        this.addAbility(ability);
    }

    private KnollspineInvocation(final KnollspineInvocation card) {
        super(card);
    }

    @Override
    public KnollspineInvocation copy() {
        return new KnollspineInvocation(this);
    }
}

class KnollspineInvocationDiscardCost extends CostImpl implements EarlyTargetCost {

    // discard card with early target selection, so {X} mana cost can be setup after choose

    public KnollspineInvocationDiscardCost() {
        super();
        this.text = "Discard a card with mana value X";
    }

    public KnollspineInvocationDiscardCost(final KnollspineInvocationDiscardCost cost) {
        super(cost);
    }

    @Override
    public KnollspineInvocationDiscardCost copy() {
        return new KnollspineInvocationDiscardCost(this);
    }

    @Override
    public void chooseTarget(Game game, Ability source, Player controller) {
        Target target = new TargetCardInHand().withChooseHint("to discard with mana value for X");
        controller.choose(Outcome.Discard, target, source, game);
        addTarget(target);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && !controller.getHand().isEmpty();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = false;

        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        Card card = controller.getHand().get(this.getTargets().getFirstTarget(), game);
        if (card == null) {
            return false;
        }

        this.paid = controller.discard(card, true, source, game);

        return this.paid;
    }
}

enum KnollspineInvocationAdjuster implements CostAdjuster {
    instance;

    @Override
    public void prepareX(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }

        // make sure early target used
        VariableManaCost costX = ability.getManaCostsToPay().stream()
                .filter(c -> c instanceof VariableManaCost)
                .map(c -> (VariableManaCost) c)
                .findFirst()
                .orElse(null);
        if (costX == null) {
            throw new IllegalArgumentException("Wrong code usage: costX lost");
        }
        KnollspineInvocationDiscardCost costDiscard = ability.getCosts().stream()
                .filter(c -> c instanceof KnollspineInvocationDiscardCost)
                .map(c -> (KnollspineInvocationDiscardCost) c)
                .findFirst()
                .orElse(null);
        if (costDiscard == null) {
            throw new IllegalArgumentException("Wrong code usage: costDiscard lost");
        }

        if (game.inCheckPlayableState()) {
            // possible X
            int minManaValue = controller.getHand().getCards(game).stream()
                    .mapToInt(MageObject::getManaValue)
                    .min()
                    .orElse(0);
            int maxManaValue = controller.getHand().getCards(game).stream()
                    .mapToInt(MageObject::getManaValue)
                    .max()
                    .orElse(0);
            ability.setVariableCostsMinMax(minManaValue, maxManaValue);
        } else {
            // real X
            Card card = controller.getHand().get(costDiscard.getTargets().getFirstTarget(), game);
            if (card == null) {
                throw new IllegalStateException("Wrong code usage: card to discard lost");
            }
            ability.setVariableCostsValue(card.getManaValue());
        }
    }
}
