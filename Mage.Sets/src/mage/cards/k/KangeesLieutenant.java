package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KangeesLieutenant extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("attacking creatures with flying");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public KangeesLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kangee's Lieutenant attacks, attacking creatures with flying get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(
                1, 1, Duration.EndOfTurn, filter, false
        ), false));

        // Encore {5}{W}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{5}{W}")));
    }

    private KangeesLieutenant(final KangeesLieutenant card) {
        super(card);
    }

    @Override
    public KangeesLieutenant copy() {
        return new KangeesLieutenant(this);
    }
}
