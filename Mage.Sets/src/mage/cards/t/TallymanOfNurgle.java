package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TallymanOfNurgle extends CardImpl {

    public TallymanOfNurgle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // The Seven-fold Chant -- At the beginning of your end step, if a creature died this turn, you draw a card and you lose 1 life. If seven or more creatures died this turn, instead you draw seven cards and you lose 7 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(7), new DrawCardSourceControllerEffect(1),
                        TallymanOfNurgleCondition.instance, "you draw a card and you lose 1 life. " +
                        "If seven or more creatures died this turn, instead you draw seven cards and you lose 7 life"
                ).addEffect(new LoseLifeSourceControllerEffect(7)).addOtherwiseEffect(new LoseLifeSourceControllerEffect(1)),
                TargetController.YOU, MorbidCondition.instance, false
        ).withFlavorWord("The Seven-fold Chant").addHint(CreaturesDiedThisTurnHint.instance));
    }

    private TallymanOfNurgle(final TallymanOfNurgle card) {
        super(card);
    }

    @Override
    public TallymanOfNurgle copy() {
        return new TallymanOfNurgle(this);
    }
}

enum TallymanOfNurgleCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CreaturesDiedThisTurnCount.instance.calculate(game, source, null) >= 7;
    }
}