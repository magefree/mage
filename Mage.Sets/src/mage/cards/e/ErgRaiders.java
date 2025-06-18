package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class ErgRaiders extends CardImpl {

    private static final Condition condition = new InvertCondition(
            AttackedThisTurnSourceCondition.instance, "{this} didn't attack this turn"
    );

    public ErgRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if Erg Raiders didn't attack this turn, Erg Raiders deals 2 damage to you unless it came under your control this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(
                new DamageControllerEffect(2), ErgRaidersCondition.instance,
                "it deals 2 damage to you unless it came under your control this turn"
        )).withInterveningIf(condition));
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
