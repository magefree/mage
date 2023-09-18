package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaliciousInvader extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "an opponent controls a Human");

    private static final Condition condition = new OpponentControlsPermanentCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "An opponent controls a Human");

    public MaliciousInvader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Malicious Invader gets +2/+0 as long as an opponent controls a Human.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                condition, "{this} gets +2/+0 as long as an opponent controls a Human"
        )).addHint(hint));
    }

    private MaliciousInvader(final MaliciousInvader card) {
        super(card);
    }

    @Override
    public MaliciousInvader copy() {
        return new MaliciousInvader(this);
    }
}
