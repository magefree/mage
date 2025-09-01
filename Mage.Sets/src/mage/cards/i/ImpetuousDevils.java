
package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ImpetuousDevils extends CardImpl {

    public ImpetuousDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Impetuous Devils attacks, up to one target creature defending player controls blocks it this combat if able.
        TriggeredAbility ability = new AttacksTriggeredAbility(new MustBeBlockedByTargetSourceEffect(Duration.EndOfCombat)
                .setText("up to one target creature defending player controls blocks it this combat if able"), false, null, SetTargetPointer.PLAYER);
        ability.setTriggerPhrase("When {this} attacks, ");
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        // At the beginning of the end step, sacrifice Impetuous Devils.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.NEXT, new SacrificeSourceEffect(), false));
    }

    private ImpetuousDevils(final ImpetuousDevils card) {
        super(card);
    }

    @Override
    public ImpetuousDevils copy() {
        return new ImpetuousDevils(this);
    }
}
