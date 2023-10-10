package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DaxosSpiritToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DaxosTheReturned extends CardImpl {

    public DaxosTheReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an enchantment spell, you get an experience counter.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        ), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false));

        // {1}{W}{B}: Create a white and black Spirit enchantment creature token. It has
        // "This creature's power and toughness are each equal to the number of experience counters you have."
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DaxosSpiritToken(), 1), new ManaCostsImpl<>("{1}{W}{B}")));
    }

    private DaxosTheReturned(final DaxosTheReturned card) {
        super(card);
    }

    @Override
    public DaxosTheReturned copy() {
        return new DaxosTheReturned(this);
    }
}
