
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Spitebellows extends CardImpl {

    public Spitebellows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // When Spitebellows leaves the battlefield, it deals 6 damage to target creature.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new DamageTargetEffect(6, "it"), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Evoke {1}{R}{R}
        this.addAbility(new EvokeAbility("{1}{R}{R}"));
    }

    private Spitebellows(final Spitebellows card) {
        super(card);
    }

    @Override
    public Spitebellows copy() {
        return new Spitebellows(this);
    }
}
