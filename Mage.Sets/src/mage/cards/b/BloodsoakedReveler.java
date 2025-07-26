package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.BloodToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodsoakedReveler extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition();
    private static final Hint hint = new ConditionHint(condition, "You gained life this turn");

    public BloodsoakedReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // At the beginning of your end step, if you gained life this turn, create a Blood token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new BloodToken()),
                false, condition
        ).addHint(hint), new PlayerGainedLifeWatcher());

        // {4}{B}: Each opponent loses 2 life and you gain 2 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{4}{B}")
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private BloodsoakedReveler(final BloodsoakedReveler card) {
        super(card);
    }

    @Override
    public BloodsoakedReveler copy() {
        return new BloodsoakedReveler(this);
    }
}
