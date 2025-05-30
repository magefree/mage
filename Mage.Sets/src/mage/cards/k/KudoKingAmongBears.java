package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KudoKingAmongBears extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KudoKingAmongBears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other creatures have base power and toughness 2/2 and are Bears in addition to their other types.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessAllEffect(
                2, 2, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new AddCardSubtypeAllEffect(
                filter, SubType.BEAR, DependencyType.AddingCreatureType
        ).setText("and are Bears in addition to their other types"));
        this.addAbility(ability);
    }

    private KudoKingAmongBears(final KudoKingAmongBears card) {
        super(card);
    }

    @Override
    public KudoKingAmongBears copy() {
        return new KudoKingAmongBears(this);
    }
}
