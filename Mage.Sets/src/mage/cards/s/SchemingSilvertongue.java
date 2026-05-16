package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SchemingSilvertongue extends PrepareCard {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.OR_GREATER, 2);
    private static final Hint hint = ControllerGainedLifeCount.getHint();

    public SchemingSilvertongue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}", "Sign in Blood", CardType.SORCERY, "{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying, lifelink
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your second main phase, if you gained 2 or more life this turn, this creature becomes prepared.
        Ability ability = new BeginningOfSecondMainTriggeredAbility(new BecomePreparedSourceEffect(), false)
                .withInterveningIf(condition)
                .addHint(hint);
        this.addAbility(ability, new PlayerGainedLifeWatcher());

        // Sign in Blood
        // Sorcery {B}{B}
        // Target player draws two cards and loses 2 life.
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and"));
    }

    private SchemingSilvertongue(final SchemingSilvertongue card) {
        super(card);
    }

    @Override
    public SchemingSilvertongue copy() {
        return new SchemingSilvertongue(this);
    }
}
