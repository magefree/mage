
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class KorAeronaut extends CardImpl {

    public KorAeronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Kicker {1}{W} (You may pay an additional {1}{W} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{W}"));

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //When Kor Aeronaut enters the battlefield, if it was kicked, target creature gains flying until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, target creature gains flying until end of turn."));
    }

    private KorAeronaut(final KorAeronaut card) {
        super(card);
    }

    @Override
    public KorAeronaut copy() {
        return new KorAeronaut(this);
    }
}
