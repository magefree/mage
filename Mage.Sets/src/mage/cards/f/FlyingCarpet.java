
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class FlyingCarpet extends CardImpl {

    public FlyingCarpet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FlyingCarpet(final FlyingCarpet card) {
        super(card);
    }

    @Override
    public FlyingCarpet copy() {
        return new FlyingCarpet(this);
    }
}
