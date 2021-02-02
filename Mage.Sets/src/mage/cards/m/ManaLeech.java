
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
public final class ManaLeech extends CardImpl {

    public ManaLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Mana Leech during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {tap}: Tap target land. It doesn't untap during its controller's untap step for as long as Mana Leech remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        this.addAbility(ability);
    }

    private ManaLeech(final ManaLeech card) {
        super(card);
    }

    @Override
    public ManaLeech copy() {
        return new ManaLeech(this);
    }
}
