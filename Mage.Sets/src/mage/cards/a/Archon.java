package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class Archon extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public Archon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Archon enters the battlefield, exile another permanent you control until Archon leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT.getMessage())
                .setText("exile another permanent you control until {this} leaves the battlefield"));
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()).setText(""));
        this.addAbility(ability);
    }

    public Archon(final Archon card) {
        super(card);
    }

    @Override
    public Archon copy() {
        return new Archon(this);
    }
}
