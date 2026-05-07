package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class SchemingSilvertongue extends PrepareCard {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.OR_GREATER, 2);
    private static final Hint hint = new ConditionHint(condition);

    public SchemingSilvertongue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}", "Sign in Blood", new CardType[]{CardType.SORCERY}, "{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your second main phase, if you gained 2 or more life this turn, this creature becomes prepared.
        this.addAbility(new BeginningOfSecondMainTriggeredAbility(
            new BecomePreparedSourceEffect(), false
        ).withInterveningIf(condition).addHint(hint), new PlayerGainedLifeWatcher());

        // Sign in Blood
        // Sorcery {B}{B}
        // Target player draws two cards and loses 2 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private SchemingSilvertongue(final SchemingSilvertongue card) {
        super(card);
    }

    @Override
    public SchemingSilvertongue copy() {
        return new SchemingSilvertongue(this);
    }
}
