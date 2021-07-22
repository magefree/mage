
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Glarewielder extends CardImpl {

    public Glarewielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Glarewielder enters the battlefield, up to two target creatures can't block this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
        // Evoke {1}{R}
        this.addAbility(new EvokeAbility("{1}{R}"));
    }

    private Glarewielder(final Glarewielder card) {
        super(card);
    }

    @Override
    public Glarewielder copy() {
        return new Glarewielder(this);
    }
}
