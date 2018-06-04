
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class SickeningDreams extends CardImpl {

    public SickeningDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // As an additional cost to cast Sickening Dreams, discard X cards.
        this.getSpellAbility().addCost(new SickeningDreamsAdditionalCost());
        
        // Sickening Dreams deals X damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(new GetXValue(), new FilterCreaturePermanent()));
    }

    public SickeningDreams(final SickeningDreams card) {
        super(card);
    }

    @Override
    public SickeningDreams copy() {
        return new SickeningDreams(this);
    }
}

class SickeningDreamsAdditionalCost extends VariableCostImpl {

    SickeningDreamsAdditionalCost() {
        super("cards to discard");
        this.text = "as an additional cost to cast this spell, discard X cards";
    }

    SickeningDreamsAdditionalCost(final SickeningDreamsAdditionalCost cost) {
        super(cost);
    }

    @Override
    public SickeningDreamsAdditionalCost copy() {
        return new SickeningDreamsAdditionalCost(this);
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
        TargetCardInHand target = new TargetCardInHand(xValue, new FilterCard());
        return new DiscardTargetCost(target);
    }
}