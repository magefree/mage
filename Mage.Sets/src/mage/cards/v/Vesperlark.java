package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Vesperlark extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with power 1 or less from your graveyard");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 2));
    }

    public Vesperlark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Vesperlark leaves the battlefield, return target creature card with power 1 or less from your graveyard to the battlefield.
        Ability ability = new LeavesBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Evoke {1}{W}
        this.addAbility(new EvokeAbility("{1}{W}"));
    }

    private Vesperlark(final Vesperlark card) {
        super(card);
    }

    @Override
    public Vesperlark copy() {
        return new Vesperlark(this);
    }
}
