package mage.cards.e;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveDerailment extends CardImpl {

    public ExplosiveDerailment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2} -- Explosive Derailment deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(2));

        // + {2} -- Destroy target artifact.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetArtifactPermanent())
                .withCost(new GenericManaCost(2)));
    }

    private ExplosiveDerailment(final ExplosiveDerailment card) {
        super(card);
    }

    @Override
    public ExplosiveDerailment copy() {
        return new ExplosiveDerailment(this);
    }
}
