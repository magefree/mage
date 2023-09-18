package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.DiscardCostCardManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NahirisWrath extends CardImpl {

    public NahirisWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // As an additional cost to cast Nahiri's Wrath, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));

        // Nahiri's Wrath deals damage equal to the total converted mana cost of the discarded cards to each of up to X target creatures and/or planeswalkers.
        Effect effect = new DamageTargetEffect(DiscardCostCardManaValue.instance);
        effect.setText("{this} deals damage equal to the total mana value of the discarded cards to each of up to X target creatures and/or planeswalkers");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(NahirisWrathAdjuster.instance);
    }

    private NahirisWrath(final NahirisWrath card) {
        super(card);
    }

    @Override
    public NahirisWrath copy() {
        return new NahirisWrath(this);
    }
}

enum NahirisWrathAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numTargets = 0;
        for (VariableCost cost : ability.getCosts().getVariableCosts()) {
            if (cost instanceof DiscardXTargetCost) {
                numTargets = cost.getAmount();
                break;
            }
        }
        if (numTargets > 0) {
            ability.addTarget(new TargetCreatureOrPlaneswalker(0, numTargets, new FilterCreatureOrPlaneswalkerPermanent(), false));
        }
    }
}