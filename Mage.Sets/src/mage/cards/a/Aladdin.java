package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class Aladdin extends CardImpl {

    public Aladdin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}{R}, {tap}: Gain control of target artifact for as long as you control Aladdin.
        Ability ability = new SimpleActivatedAbility(
                new GainControlTargetEffect(Duration.WhileControlled), new ManaCostsImpl<>("{1}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private Aladdin(final Aladdin card) {
        super(card);
    }

    @Override
    public Aladdin copy() {
        return new Aladdin(this);
    }
}
