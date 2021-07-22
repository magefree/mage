package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class WolfOfDevilsBreach extends CardImpl {

    public WolfOfDevilsBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Wolf of Devil's Breach attacks, you may pay {1}{R} and discard a card. If you do, Wolf of Devil's Breach deals
        // damage to target creature or planeswalker equal to the discarded card's converted mana cost.
        Costs toPay = new CostsImpl<>();
        toPay.add(new ManaCostsImpl<>("{1}{R}"));
        toPay.add(new DiscardCardCost());
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(new DamageTargetEffect(new WolfOfDevilsBreachDiscardCostCardConvertedManaCount()), toPay,
                "Pay {1}{R} and discard a card to let {this} do damage to target creature or planeswalker equal to the discarded card's mana value?", true), false,
                "Whenever {this} attacks, you may pay {1}{R} and discard a card. If you do, {this} deals damage to target creature or planeswalker "
                + "equal to the discarded card's mana value.");
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private WolfOfDevilsBreach(final WolfOfDevilsBreach card) {
        super(card);
    }

    @Override
    public WolfOfDevilsBreach copy() {
        return new WolfOfDevilsBreach(this);
    }
}

class WolfOfDevilsBreachDiscardCostCardConvertedManaCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Effect sourceEffect : sourceAbility.getEffects()) {
            if (sourceEffect instanceof DoIfCostPaid) {
                Cost doCosts = ((DoIfCostPaid) sourceEffect).getCost();
                if (doCosts instanceof Costs) {
                    Costs costs = (Costs) doCosts;
                    for (Object cost : costs) {
                        if (cost instanceof DiscardCardCost) {
                            DiscardCardCost discardCost = (DiscardCardCost) cost;
                            int cmc = 0;
                            for (Card card : discardCost.getCards()) {
                                cmc += card.getManaValue();
                            }
                            return cmc;
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public WolfOfDevilsBreachDiscardCostCardConvertedManaCount copy() {
        return new WolfOfDevilsBreachDiscardCostCardConvertedManaCount();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "the discarded card's mana value";
    }
}
