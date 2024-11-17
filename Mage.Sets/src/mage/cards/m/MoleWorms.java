
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class MoleWorms extends CardImpl {

    public MoleWorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Mole Worms during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {tap}: Tap target land. It doesn't untap during its controller's untap step for as long as Mole Worms remains tapped.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        this.addAbility(ability);
    }

    private MoleWorms(final MoleWorms card) {
        super(card);
    }

    @Override
    public MoleWorms copy() {
        return new MoleWorms(this);
    }
}
