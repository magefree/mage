package mage.cards.s;

import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SickeningDreams extends CardImpl {

    public SickeningDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast Sickening Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));

        // Sickening Dreams deals X damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(GetXValue.instance, new FilterCreaturePermanent()));
    }

    private SickeningDreams(final SickeningDreams card) {
        super(card);
    }

    @Override
    public SickeningDreams copy() {
        return new SickeningDreams(this);
    }
}