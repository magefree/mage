package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaneclawMarauder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public BaneclawMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Whenever Baneclaw Marauder becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ).setText("each creature blocking it gets -1/-1 until end of turn"), false));

        // Whenever a creature blocking Baneclaw Marauder dies, its controller loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new LoseLifeTargetControllerEffect(1)
                        .setText("that creature's controller loses 1 life"),
                false, filter, true
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private BaneclawMarauder(final BaneclawMarauder card) {
        super(card);
    }

    @Override
    public BaneclawMarauder copy() {
        return new BaneclawMarauder(this);
    }
}
