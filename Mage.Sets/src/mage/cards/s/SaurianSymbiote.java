package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaurianSymbiote extends CardImpl {

    public SaurianSymbiote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Saurian Symbiote enters, choose one--
        // * Put a +1/+1 counter on Saurian Symbiote.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        // * Create a 1/1 green Saproling creature token.
        ability.addMode(new Mode(new CreateTokenEffect(new SaprolingToken())));
        this.addAbility(ability);
    }

    private SaurianSymbiote(final SaurianSymbiote card) {
        super(card);
    }

    @Override
    public SaurianSymbiote copy() {
        return new SaurianSymbiote(this);
    }
}
