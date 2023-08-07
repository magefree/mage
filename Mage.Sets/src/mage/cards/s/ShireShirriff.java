package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ShireShirriff extends CardImpl {

    public static final FilterControlledPermanent filterToken = new FilterControlledPermanent("a token");

    static {
        filterToken.add(TokenPredicate.TRUE);
    }

    public ShireShirriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Shire Shirriff enters the battlefield, you may sacrifice a token.
        // When you do, exile target creature an opponent controls until Shire Shirriff leaves the battlefield.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ExileUntilSourceLeavesEffect(), false,
            "exile target creature an opponent controls until {this} leaves the battlefield"
        );
        reflexive.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            reflexive,
            new SacrificeTargetCost(filterToken),
            "Sacrifice a token?"
        ), true));
    }

    private ShireShirriff(final ShireShirriff card) {
        super(card);
    }

    @Override
    public ShireShirriff copy() {
        return new ShireShirriff(this);
    }
}
