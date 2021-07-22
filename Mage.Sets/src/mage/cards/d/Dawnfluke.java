
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Dawnfluke extends CardImpl {

    public Dawnfluke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Dawnfluke enters the battlefield, prevent the next 3 damage that would be dealt to any target this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PreventDamageToTargetEffect(Duration.EndOfTurn, 3));
        Target target = new TargetAnyTarget();
        ability.addTarget(target);
        this.addAbility(ability);
        // Evoke {W}
        this.addAbility(new EvokeAbility("{W}"));
    }

    private Dawnfluke(final Dawnfluke card) {
        super(card);
    }

    @Override
    public Dawnfluke copy() {
        return new Dawnfluke(this);
    }
}
