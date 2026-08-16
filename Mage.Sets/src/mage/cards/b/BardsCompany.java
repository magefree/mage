package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class BardsCompany extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.HUMAN, "you control a Human")
    );

    public BardsCompany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may cast this spell as though it had flash if you control a Human.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(
            condition, "you may cast this spell as though it had flash if you control a Human."
        ));

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)));

        // Whenever this creature enters or attacks, recruit.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new RecruitEffect()));
    }

    private BardsCompany(final BardsCompany card) {
        super(card);
    }

    @Override
    public BardsCompany copy() {
        return new BardsCompany(this);
    }
}
