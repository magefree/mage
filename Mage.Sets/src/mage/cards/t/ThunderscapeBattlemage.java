
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class ThunderscapeBattlemage extends CardImpl {

    public ThunderscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{B} and/or {G}
        KickerAbility kickerAbility = new KickerAbility("{1}{B}");
        kickerAbility.addKickerCost("{G}");
        this.addAbility(kickerAbility);

        // When {this} enters the battlefield, if it was kicked with its {1}{B} kicker, target player discards two cards.
        TriggeredAbility ability1 = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability1.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability1, new KickedCostCondition("{1}{B}"),
                "When {this} enters the battlefield, if it was kicked with its {1}{B} kicker, target player discards two cards."));

        // When {this} enters the battlefield, if it was kicked with its {G} kicker, destroy target enchantment.
        TriggeredAbility ability2 = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability2.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability2, new KickedCostCondition("{G}"),
                "When {this} enters the battlefield, if it was kicked with its {G} kicker, destroy target enchantment."));
    }

    private ThunderscapeBattlemage(final ThunderscapeBattlemage card) {
        super(card);
    }

    @Override
    public ThunderscapeBattlemage copy() {
        return new ThunderscapeBattlemage(this);
    }
}
