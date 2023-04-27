package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishHydromancer extends CardImpl {

    public ElvishHydromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Kicker {3}{U}
        this.addAbility(new KickerAbility("{3}{U}"));

        // When Elvish Hydromancer enters the battlefield, if it was kicked, create a token that's a copy of target creature you control.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenCopyTargetEffect()),
                KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, create a token that's a copy of target creature you control."
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ElvishHydromancer(final ElvishHydromancer card) {
        super(card);
    }

    @Override
    public ElvishHydromancer copy() {
        return new ElvishHydromancer(this);
    }
}
