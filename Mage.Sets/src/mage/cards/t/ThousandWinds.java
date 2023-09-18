
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LevelX2
 */
public final class ThousandWinds extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other tapped creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.TAPPED);
    }

    public ThousandWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {5}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{U}{U}")));
        // When Thousand Winds is turned face up, return all other tapped creatures to their owners' hands.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter)));
    }

    private ThousandWinds(final ThousandWinds card) {
        super(card);
    }

    @Override
    public ThousandWinds copy() {
        return new ThousandWinds(this);
    }
}
