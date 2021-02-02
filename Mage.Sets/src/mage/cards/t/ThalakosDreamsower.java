
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ThalakosDreamsower extends CardImpl {

    public ThalakosDreamsower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.THALAKOS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // You may choose not to untap Thalakos Dreamsower during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // Whenever Thalakos Dreamsower deals damage to an opponent, tap target creature. That creature doesn't untap during its controller's untap step for as long as Thalakos Dreamsower remains tapped.
        Ability ability = new DealsDamageToOpponentTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        this.addAbility(ability);
    }

    private ThalakosDreamsower(final ThalakosDreamsower card) {
        super(card);
    }

    @Override
    public ThalakosDreamsower copy() {
        return new ThalakosDreamsower(this);
    }
}
