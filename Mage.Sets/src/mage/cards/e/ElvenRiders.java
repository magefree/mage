
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Quercitron
 */
public final class ElvenRiders extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Walls and/or creatures with flying");

    static {
        filter.add(Predicates.not(Predicates.or(SubType.WALL.getPredicate(), new AbilityPredicate(FlyingAbility.class))));
    }

    public ElvenRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Elven Riders can't be blocked except by Walls and/or creatures with flying.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private ElvenRiders(final ElvenRiders card) {
        super(card);
    }

    @Override
    public ElvenRiders copy() {
        return new ElvenRiders(this);
    }
}
