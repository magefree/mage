
package mage.cards.p;

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
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox
 */
public final class PhyrexianGremlins extends CardImpl {

    public PhyrexianGremlins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Phyrexian Gremlins during your untap step.
         this.addAbility(new SkipUntapOptionalAbility());
       // {tap}: Tap target artifact. It doesn't untap during its controller's untap step for as long as Phyrexian Gremlins remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        this.addAbility(ability);
    }

    private PhyrexianGremlins(final PhyrexianGremlins card) {
        super(card);
    }

    @Override
    public PhyrexianGremlins copy() {
        return new PhyrexianGremlins(this);
    }
}
