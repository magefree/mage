package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DayboundAbility;
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
public final class BanebladeScoundrel extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public BanebladeScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BaneclawMarauder.class;

        // Whenever Baneblade Scoundrel becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ).setText("each creature blocking it gets -1/-1 until end of turn"), false));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private BanebladeScoundrel(final BanebladeScoundrel card) {
        super(card);
    }

    @Override
    public BanebladeScoundrel copy() {
        return new BanebladeScoundrel(this);
    }
}
