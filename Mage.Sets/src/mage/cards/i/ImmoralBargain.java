package mage.cards.i;

import java.util.UUID;

import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

/**
 *
 * @author muz
 */
public final class ImmoralBargain extends CardImpl {

    public ImmoralBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{G}");

        // As an additional cost to cast this spell, sacrifice X creatures.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledCreaturePermanent("creatures"), false));

        // Destroy X target nonland permanents.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setText("Destroy X target nonland permanents"));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private ImmoralBargain(final ImmoralBargain card) {
        super(card);
    }

    @Override
    public ImmoralBargain copy() {
        return new ImmoralBargain(this);
    }
}
