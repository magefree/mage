
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class ExtractorDemon extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public ExtractorDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature leaves the battlefield, you may have target player put the top two cards of their library into their graveyard.
        Ability ability = new LeavesBattlefieldAllTriggeredAbility(new PutLibraryIntoGraveTargetEffect(2), filter, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Unearth {2}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl("{2}{B}")));
    }

    public ExtractorDemon(final ExtractorDemon card) {
        super(card);
    }

    @Override
    public ExtractorDemon copy() {
        return new ExtractorDemon(this);
    }
}
