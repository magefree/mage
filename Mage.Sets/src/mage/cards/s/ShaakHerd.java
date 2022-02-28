
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Styxo
 */
public final class ShaakHerd extends CardImpl {

    private static final FilterCard filter = new FilterCard("another target creature card");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ShaakHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Shaak Herd dies, you may return another target creature card from your graveyard to your hand.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ShaakHerd(final ShaakHerd card) {
        super(card);
    }

    @Override
    public ShaakHerd copy() {
        return new ShaakHerd(this);
    }
}
