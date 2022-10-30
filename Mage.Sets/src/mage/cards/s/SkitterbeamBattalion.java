package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkitterbeamBattalion extends CardImpl {

    public SkitterbeamBattalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Prototype {3}{R}{R} -- 2/2
        this.addAbility(new PrototypeAbility(this, "{3}{R}{R}", 2, 2));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Skitterbeam Battalion enters the battlefield, if you cast it, create two tokens that are copies of it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenCopySourceEffect(2)),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, create two tokens that are copies of it."
        ));
    }

    private SkitterbeamBattalion(final SkitterbeamBattalion card) {
        super(card);
    }

    @Override
    public SkitterbeamBattalion copy() {
        return new SkitterbeamBattalion(this);
    }
}
