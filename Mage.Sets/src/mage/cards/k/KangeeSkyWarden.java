package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KangeeSkyWarden extends CardImpl {

    private static final FilterCreaturePermanent filter1
            = new FilterCreaturePermanent("attacking creatures with flying");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("blocking creatures with flying");
    private static final Predicate predicate = new AbilityPredicate(FlyingAbility.class);

    static {

        filter1.add(AttackingPredicate.instance);
        filter1.add(predicate);
        filter2.add(BlockingPredicate.instance);
        filter2.add(predicate);
    }

    public KangeeSkyWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Kangee, Sky Warden attacks, attacking creatures with flying get +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(
                2, 0, Duration.EndOfTurn, filter1, false
        ), false));

        // Whenever Kangee blocks, blocking creatures with flying get +0/+2 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostAllEffect(
                0, 2, Duration.EndOfTurn, filter2, false
        ), false));
    }

    private KangeeSkyWarden(final KangeeSkyWarden card) {
        super(card);
    }

    @Override
    public KangeeSkyWarden copy() {
        return new KangeeSkyWarden(this);
    }
}
