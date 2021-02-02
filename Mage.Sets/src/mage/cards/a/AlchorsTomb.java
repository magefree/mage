
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class AlchorsTomb extends CardImpl {

    public AlchorsTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}: Target permanent you control becomes the color of your choice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorTargetEffect(Duration.WhileOnBattlefield), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private AlchorsTomb(final AlchorsTomb card) {
        super(card);
    }

    @Override
    public AlchorsTomb copy() {
        return new AlchorsTomb(this);
    }
}
