
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class AvalancheTusker extends CardImpl {

    public AvalancheTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}{R}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whenever Avalanche Tusker attacks, target creature defending player controls blocks it this turn if able.
        Ability ability = new AttacksTriggeredAbility(new MustBeBlockedByTargetSourceEffect(Duration.EndOfCombat)
                .setText("target creature defending player controls blocks it this combat if able"), false, null, SetTargetPointer.PLAYER);
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private AvalancheTusker(final AvalancheTusker card) {
        super(card);
    }

    @Override
    public AvalancheTusker copy() {
        return new AvalancheTusker(this);
    }
}
