
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author noxx
 */
public final class DriverOfTheDead extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DriverOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Driver of the Dead dies, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        Target target = new TargetCardInYourGraveyard(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private DriverOfTheDead(final DriverOfTheDead card) {
        super(card);
    }

    @Override
    public DriverOfTheDead copy() {
        return new DriverOfTheDead(this);
    }
}
