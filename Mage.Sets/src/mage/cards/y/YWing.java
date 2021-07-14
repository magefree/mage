
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class YWing extends CardImpl {

    public YWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Y-Wing can't block
        this.addAbility(new CantBlockAbility());

        // Whenever Y-Wing can't deals combat damage to a player, tap target creature an opponent controls. It doesn't untap during its controller's next untap step.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new TapTargetEffect("tap target creature an opponent controls."), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect(" It"));
        this.addAbility(ability);
    }

    private YWing(final YWing card) {
        super(card);
    }

    @Override
    public YWing copy() {
        return new YWing(this);
    }
}
