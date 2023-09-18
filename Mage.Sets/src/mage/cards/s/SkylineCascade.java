
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SkylineCascade extends CardImpl {

    public SkylineCascade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Skyline Cascade enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Skyline Cascade enters the battlefield, target creature an opponent controls doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DontUntapInControllersNextUntapStepTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private SkylineCascade(final SkylineCascade card) {
        super(card);
    }

    @Override
    public SkylineCascade copy() {
        return new SkylineCascade(this);
    }
}
