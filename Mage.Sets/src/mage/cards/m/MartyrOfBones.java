package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MartyrOfBones extends CardImpl {

    public MartyrOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Reveal X black cards from your hand, Sacrifice Martyr of Bones: Exile up to X target cards from a single graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new GenericManaCost(1));
        ability.addCost(new RevealVariableBlackCardsFromHandCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 1, StaticFilters.FILTER_CARD_CARDS));
        ability.setTargetAdjuster(MartyrOfBonesAdjuster.instance);
        this.addAbility(ability);
    }

    private MartyrOfBones(final MartyrOfBones card) {
        super(card);
    }

    @Override
    public MartyrOfBones copy() {
        return new MartyrOfBones(this);
    }
}

enum MartyrOfBonesAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int amount = 0;
        for (Cost cost : ability.getCosts()) {
            if (cost instanceof RevealVariableBlackCardsFromHandCost) {
                amount = ((VariableCost) cost).getAmount();
            }
        }
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInASingleGraveyard(0, amount, StaticFilters.FILTER_CARD_CARDS));
    }
}

class RevealVariableBlackCardsFromHandCost extends VariableCostImpl {

    private static final FilterCard filter = new FilterCard("X black cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    RevealVariableBlackCardsFromHandCost() {
        super(VariableCostType.NORMAL, "black cards to reveal");
        this.text = "Reveal " + xText + " black cards from your hand";
    }

    RevealVariableBlackCardsFromHandCost(final RevealVariableBlackCardsFromHandCost cost) {
        super(cost);
    }

    @Override
    public RevealVariableBlackCardsFromHandCost copy() {
        return new RevealVariableBlackCardsFromHandCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RevealTargetFromHandCost(new TargetCardInHand(0, xValue, filter));
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return 0;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            return player.getHand().getCards(filter, game).size();
        }
        return 0;
    }
}
