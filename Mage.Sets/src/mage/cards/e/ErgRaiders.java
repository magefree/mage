package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author awjackson
 *
 */
public final class ErgRaiders extends CardImpl {

    public ErgRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if Erg Raiders didn't attack this turn, Erg Raiders deals 2 damage to you unless it came under your control this turn.
        Effect effect = new ConditionalOneShotEffect(
                new DamageControllerEffect(2),
                ErgRaidersCondition.instance,
                "{this} deals 2 damage to you unless it came under your control this turn"
        );
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(effect, TargetController.YOU, false),
                new InvertCondition(AttackedThisTurnSourceCondition.instance),
                "At the beginning of your end step, if {this} didn't attack this turn, {this} deals 2 damage to you unless it came under your control this turn.");
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);
    }

    private ErgRaiders(final ErgRaiders card) {
        super(card);
    }

    @Override
    public ErgRaiders copy() {
        return new ErgRaiders(this);
    }
}

enum ErgRaidersCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent raiders = source.getSourcePermanentOrLKI(game);
        return raiders != null && raiders.wasControlledFromStartOfControllerTurn();
    }
}
