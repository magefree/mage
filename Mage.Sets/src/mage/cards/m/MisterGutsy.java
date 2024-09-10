package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.JunkToken;

/**
 * @author Cguy7777
 */
public final class MisterGutsy extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Aura or Equipment spell");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()));
    }

    public MisterGutsy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast an Aura or Equipment spell, put a +1/+1 counter on Mister Gutsy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false));

        // When Mister Gutsy dies, create X Junk tokens, where X is the number of +1/+1 counters on it.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new JunkToken(), new CountersSourceCount(CounterType.P1P1))
                .setText("create X Junk tokens, where X is the number of +1/+1 counters on it. " +
                        "<i>(They're artifacts with \"{T}, Sacrifice this artifact: Exile the top card of your library. " +
                        "You may play that card this turn. Activate only as a sorcery.\")</i>")));
    }

    private MisterGutsy(final MisterGutsy card) {
        super(card);
    }

    @Override
    public MisterGutsy copy() {
        return new MisterGutsy(this);
    }
}
