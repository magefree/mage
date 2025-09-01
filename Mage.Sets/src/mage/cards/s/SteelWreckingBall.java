package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SteelWreckingBall extends CardImpl {

    public SteelWreckingBall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        

        // When this artifact enters, it deals 5 damage to target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5, "it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {1}{R}, Discard this card: Destroy target artifact.
        Ability discardAbility = new SimpleActivatedAbility(Zone.HAND, new DestroyTargetEffect(),
                new ManaCostsImpl<>("{1}{R}"));
        discardAbility.addCost(new DiscardSourceCost());
        discardAbility.addTarget(new TargetArtifactPermanent());
        this.addAbility(discardAbility);
    }

    private SteelWreckingBall(final SteelWreckingBall card) {
        super(card);
    }

    @Override
    public SteelWreckingBall copy() {
        return new SteelWreckingBall(this);
    }
}
