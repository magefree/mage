
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class MartyrOfBones extends CardImpl {

    private final UUID originalId;

    public MartyrOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //TODO: Make ability properly copiable
        // {1}, Reveal X black cards from your hand, Sacrifice Martyr of Bones: Exile up to X target cards from a single graveyard.
        Effect effect = new ExileTargetEffect(null, null, Zone.GRAVEYARD);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1));
        ability.addCost(new RevealVariableBlackCardsFromHandCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 1, new FilterCard("cards in a single graveyard")));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public MartyrOfBones(final MartyrOfBones card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            int amount = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof RevealVariableBlackCardsFromHandCost) {
                    amount = ((VariableCost) cost).getAmount();
                }
            }
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInASingleGraveyard(0, amount, new FilterCard()));
        }
    }

    @Override
    public MartyrOfBones copy() {
        return new MartyrOfBones(this);
    }
}

class RevealVariableBlackCardsFromHandCost extends VariableCostImpl {

    private static final FilterCard filter = new FilterCard("X black cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    RevealVariableBlackCardsFromHandCost() {
        super("black cards to reveal");
        this.text = new StringBuilder("Reveal ").append(xText).append(" black cards from {this}").toString();
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
