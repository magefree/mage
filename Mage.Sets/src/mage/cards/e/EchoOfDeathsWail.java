package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author weirddan455
 */
public final class EchoOfDeathsWail extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.RAT, "all Rat tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public EchoOfDeathsWail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Echo of Death's Wail enters the battlefield, gain control of all Rat tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainControlAllEffect(Duration.Custom, filter)));

        // Whenever Echo of Death's Wail attacks, you may sacrifice another creature. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        )));
    }

    private EchoOfDeathsWail(final EchoOfDeathsWail card) {
        super(card);
    }

    @Override
    public EchoOfDeathsWail copy() {
        return new EchoOfDeathsWail(this);
    }
}
