package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalierOfNight extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CavalierOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Cavalier of Night enters the battlefield, you may sacrifice another creature. When you do, destroy target creature an opponent controls.
        ReflexiveTriggeredAbility triggeredAbility = new ReflexiveTriggeredAbility(
                new DestroyTargetEffect(), false, "destroy target creature an opponent controls"
        );
        triggeredAbility.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                triggeredAbility, new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)),
                "Sacrifice a creature?"
        )));

        // When Cavalier of Night dies, return target creature card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private CavalierOfNight(final CavalierOfNight card) {
        super(card);
    }

    @Override
    public CavalierOfNight copy() {
        return new CavalierOfNight(this);
    }
}
