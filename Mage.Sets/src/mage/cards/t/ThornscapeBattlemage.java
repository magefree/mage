
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author FenrisulfrX
 */
public final class ThornscapeBattlemage extends CardImpl {

    public ThornscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {R} and/or {W}
        KickerAbility kickerAbility = new KickerAbility("{R}");
        kickerAbility.addKickerCost("{W}");
        this.addAbility(kickerAbility);

        // When {this} enters the battlefield, if it was kicked with its {R} kicker, it deals 2 damage to any target.
        TriggeredAbility ability1 = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability1.addTarget(new TargetAnyTarget());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability1, new KickedCostCondition("{R}"),
                "When {this} enters the battlefield, if it was kicked with its {R} kicker, it deals 2 damage to any target."));

        // When {this} enters the battlefield, if it was kicked with its {W} kicker, destroy target artifact.
        TriggeredAbility ability2 = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability2.addTarget(new TargetArtifactPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability2, new KickedCostCondition("{W}"),
                "When {this} enters the battlefield, if it was kicked with its {W} kicker, destroy target artifact."));
    }

    private ThornscapeBattlemage(final ThornscapeBattlemage card) {
        super(card);
    }

    @Override
    public ThornscapeBattlemage copy() {
        return new ThornscapeBattlemage(this);
    }
}
