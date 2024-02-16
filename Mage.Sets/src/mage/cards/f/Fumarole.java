package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author LoneFox
 */
public final class Fumarole extends CardImpl {

    public Fumarole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{R}");

        // As an additional cost to cast Fumarole, pay 3 life.
        this.getSpellAbility().addCost(new PayLifeCost(3));
        // Destroy target creature and target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private Fumarole(final Fumarole card) {
        super(card);
    }

    @Override
    public Fumarole copy() {
        return new Fumarole(this);
    }
}
