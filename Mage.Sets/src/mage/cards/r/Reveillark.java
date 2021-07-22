
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Reveillark extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with power 2 or less from your graveyard");
    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public Reveillark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Reveillark leaves the battlefield, return up to two target creature cards with power 2 or less from your graveyard to the battlefield.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(0,2,filter));
        this.addAbility(ability);
        // Evoke {5}{W}
        this.addAbility(new EvokeAbility("{5}{W}"));
    }

    private Reveillark(final Reveillark card) {
        super(card);
    }

    @Override
    public Reveillark copy() {
        return new Reveillark(this);
    }
}
