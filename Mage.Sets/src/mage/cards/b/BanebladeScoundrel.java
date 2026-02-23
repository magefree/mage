package mage.cards.b;

import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BanebladeScoundrel extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public BanebladeScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF}, "{3}{B}",
                "Baneclaw Marauder",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "B");

        // Baneblade Scoundrel
        this.getLeftHalfCard().setPT(4, 3);

        // Whenever Baneblade Scoundrel becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.getLeftHalfCard().addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ).setText("each creature blocking it gets -1/-1 until end of turn"), false));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Baneclaw Marauder
        this.getRightHalfCard().setPT(5, 4);

        // Whenever Baneclaw Marauder becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.getRightHalfCard().addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ).setText("each creature blocking it gets -1/-1 until end of turn"), false));

        // Whenever a creature blocking Baneclaw Marauder dies, its controller loses 1 life.
        this.getRightHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new LoseLifeTargetControllerEffect(1)
                        .setText("that creature's controller loses 1 life"),
                false, filter, true
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private BanebladeScoundrel(final BanebladeScoundrel card) {
        super(card);
    }

    @Override
    public BanebladeScoundrel copy() {
        return new BanebladeScoundrel(this);
    }
}
