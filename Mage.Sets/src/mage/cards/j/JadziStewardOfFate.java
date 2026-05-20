package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadziStewardOfFate extends PrepareCard {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FRACTAL);

    public JadziStewardOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}", "Oracle's Gift", new CardType[]{CardType.SORCERY}, "{X}{X}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Jadzi enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // When Jadzi enters, draw two cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(2, 2)));

        // Oracle's Gift
        // Sorcery {X}{X}{U}
        // Create X 0/0 green and blue Fractal creature tokens, then put X +1/+1 counters on each Fractal you control.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new FractalToken()));
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), GetXValue.instance, filter
        ).concatBy(", then"));
    }

    private JadziStewardOfFate(final JadziStewardOfFate card) {
        super(card);
    }

    @Override
    public JadziStewardOfFate copy() {
        return new JadziStewardOfFate(this);
    }
}
