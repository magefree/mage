
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class KorHookmaster extends CardImpl {

    public KorHookmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kor Hookmaster enters the battlefield, tap target creature an opponent controls. 
        // That creature doesn't untap during its controller's next untap step.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("that creature"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private KorHookmaster(final KorHookmaster card) {
        super(card);
    }

    @Override
    public KorHookmaster copy() {
        return new KorHookmaster(this);
    }
}
