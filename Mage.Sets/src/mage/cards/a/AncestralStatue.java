
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class AncestralStatue extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanent you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public AncestralStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Ancestral Statue enters the battlefield, return a nonland permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));
    }

    private AncestralStatue(final AncestralStatue card) {
        super(card);
    }

    @Override
    public AncestralStatue copy() {
        return new AncestralStatue(this);
    }
}
