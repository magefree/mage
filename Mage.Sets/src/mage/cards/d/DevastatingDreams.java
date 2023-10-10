package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DevastatingDreams extends CardImpl {

    public DevastatingDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}");

        // As an additional cost to cast Devastating Dreams, discard X cards at random.
        this.getSpellAbility().addCost(new DevastatingDreamsAdditionalCost());

        // Each player sacrifices X lands.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(GetXValue.instance, new FilterControlledLandPermanent("lands")));

        // Devastating Dreams deals X damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(GetXValue.instance, new FilterCreaturePermanent()));
    }

    private DevastatingDreams(final DevastatingDreams card) {
        super(card);
    }

    @Override
    public DevastatingDreams copy() {
        return new DevastatingDreams(this);
    }
}

class DevastatingDreamsAdditionalCost extends VariableCostImpl {

    DevastatingDreamsAdditionalCost() {
        super(VariableCostType.ADDITIONAL, "cards to discard randomly");
        this.text = "as an additional cost to cast this spell, discard X cards at random";
    }

    private DevastatingDreamsAdditionalCost(final DevastatingDreamsAdditionalCost cost) {
        super(cost);
    }

    @Override
    public DevastatingDreamsAdditionalCost copy() {
        return new DevastatingDreamsAdditionalCost(this);
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
        return new DiscardTargetCost(target, true);
    }
}

