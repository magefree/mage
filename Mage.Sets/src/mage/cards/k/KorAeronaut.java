package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class KorAeronaut extends CardImpl {

    public KorAeronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Kicker {1}{W} (You may pay an additional {1}{W} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{W}"));

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //When Kor Aeronaut enters the battlefield, if it was kicked, target creature gains flying until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance())
        ).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KorAeronaut(final KorAeronaut card) {
        super(card);
    }

    @Override
    public KorAeronaut copy() {
        return new KorAeronaut(this);
    }
}
