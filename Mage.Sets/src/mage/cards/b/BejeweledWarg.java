package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BejeweledWarg extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.WOLF);

    public BejeweledWarg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature deals combat damage to a player, choose one --
        // * Put a +1/+1 counter on target Wolf you control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
          new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));

        // * Create a Treasure token.
        Mode mode = new Mode(new CreateTokenEffect(new TreasureToken()));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private BejeweledWarg(final BejeweledWarg card) {
        super(card);
    }

    @Override
    public BejeweledWarg copy() {
        return new BejeweledWarg(this);
    }
}
