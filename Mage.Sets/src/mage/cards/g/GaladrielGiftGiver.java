package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaladrielGiftGiver extends CardImpl {

    public GaladrielGiftGiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Galadriel, Gift-Giver enters the battlefield or attacks, choose one --
        // * Put a +1/+1 counter on another target creature.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));

        // * Create a Food token.
        ability.addMode(new Mode(new CreateTokenEffect(new FoodToken())));

        // * Create a Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())));
        this.addAbility(ability);
    }

    private GaladrielGiftGiver(final GaladrielGiftGiver card) {
        super(card);
    }

    @Override
    public GaladrielGiftGiver copy() {
        return new GaladrielGiftGiver(this);
    }
}
