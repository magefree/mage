
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class AphettoVulture extends CardImpl {

    private static final FilterCard filter = new FilterCard("Zombie card from your graveyard");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public AphettoVulture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aphetto Vulture dies, you may put target Zombie card from your graveyard on top of your library.
        Ability ability = new DiesTriggeredAbility(new PutOnLibraryTargetEffect(true), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public AphettoVulture(final AphettoVulture card) {
        super(card);
    }

    @Override
    public AphettoVulture copy() {
        return new AphettoVulture(this);
    }
}
