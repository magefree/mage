package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.watchers.common.CreaturesDiedWatcher;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BloodlineRecollector extends PrepareCard {

    public BloodlineRecollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}", "Ancestral Craving", new CardType[]{CardType.INSTANT}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, if three or more creatures died this turn, this creature becomes prepared.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY,
            new BecomePreparedSourceEffect(),
            false,
            BloodlineRecollectorCondition.instance
        ));

        // Ancestral Craving
        // Instant {B}
        // Target player draws three cards and loses 3 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeTargetEffect(3).withTargetDescription("and"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private BloodlineRecollector(final BloodlineRecollector card) {
        super(card);
    }

    @Override
    public BloodlineRecollector copy() {
        return new BloodlineRecollector(this);
    }
}

enum BloodlineRecollectorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurn() > 2;
        }
        return false;
    }

    @Override
    public String toString() {
        return "if three or more creatures died this turn";
    }
}
