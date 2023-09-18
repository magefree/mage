package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class KnightOfValor extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("each creature without flanking blocking {this}");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlankingAbility.class)));
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public KnightOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());
        // {1}{W}: Each creature without flanking blocking Knight of Valor gets -1/-1 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false),
                new ManaCostsImpl<>("{1}{W}")
        ));
    }

    private KnightOfValor(final KnightOfValor card) {
        super(card);
    }

    @Override
    public KnightOfValor copy() {
        return new KnightOfValor(this);
    }
}
