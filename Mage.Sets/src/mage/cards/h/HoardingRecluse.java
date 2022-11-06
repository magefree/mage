package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author weirddan455
 */
public final class HoardingRecluse extends CardImpl {

    private static final FilterCard filter = new FilterCard("other card from a graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HoardingRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Hoarding Recluse dies, put up to one other target card from a graveyard on the bottom of its owner's library.
        Ability ability = new DiesSourceTriggeredAbility(new PutOnLibraryTargetEffect(false));
        ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private HoardingRecluse(final HoardingRecluse card) {
        super(card);
    }

    @Override
    public HoardingRecluse copy() {
        return new HoardingRecluse(this);
    }
}
