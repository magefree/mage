package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TauntingArbormage extends CardImpl {

    public TauntingArbormage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // When Taunting Arbormage enters the battlefield, if it was kicked, all creatures able to block target creature this turn do so.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn)),
                KickedCondition.instance, "When {this} enters the battlefield, if it was kicked, " +
                "all creatures able to block target creature this turn do so."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TauntingArbormage(final TauntingArbormage card) {
        super(card);
    }

    @Override
    public TauntingArbormage copy() {
        return new TauntingArbormage(this);
    }
}
