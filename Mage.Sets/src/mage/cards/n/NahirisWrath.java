
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.DiscardCostCardConvertedMana;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class NahirisWrath extends CardImpl {

    public NahirisWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // As an additional cost to cast Nahiri's Wrath, discard X cards.
        this.getSpellAbility().addCost(new NahirisWrathAdditionalCost());

        // Nahiri's Wrath deals damage equal to the total converted mana cost of the discarded cards to each of up to X target creatures and/or planeswalkers.
        Effect effect = new DamageTargetEffect(new DiscardCostCardConvertedMana());
        effect.setText("{this} deals damage equal to the total converted mana cost of the discarded cards to each of up to X target creatures and/or planeswalkers");
        this.getSpellAbility().addEffect(effect);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numTargets = 0;
        for (VariableCost cost : ability.getCosts().getVariableCosts()) {
            if (cost instanceof NahirisWrathAdditionalCost) {
                numTargets = ((NahirisWrathAdditionalCost) cost).getAmount();
                break;
            }
        }
        if (numTargets > 0) {
            ability.addTarget(new TargetCreatureOrPlaneswalker(0, numTargets, new FilterCreatureOrPlaneswalkerPermanent(), false));
        }
    }

    public NahirisWrath(final NahirisWrath card) {
        super(card);
    }

    @Override
    public NahirisWrath copy() {
        return new NahirisWrath(this);
    }
}

class NahirisWrathAdditionalCost extends VariableCostImpl {

    NahirisWrathAdditionalCost() {
        super("cards to discard");
        this.text = "as an additional cost to cast this spell, discard X cards";
    }

    NahirisWrathAdditionalCost(final NahirisWrathAdditionalCost cost) {
        super(cost);
    }

    @Override
    public NahirisWrathAdditionalCost copy() {
        return new NahirisWrathAdditionalCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.getHand().size();
        }
        return 0;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetCardInHand target = new TargetCardInHand(xValue, new FilterCard("cards to discard"));
        return new DiscardTargetCost(target);
    }
}
