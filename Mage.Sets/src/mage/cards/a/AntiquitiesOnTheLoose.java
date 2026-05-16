package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.Spirit22RedWhiteToken;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AntiquitiesOnTheLoose extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SPIRIT, "Spirit you control");

    public AntiquitiesOnTheLoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Create two 2/2 red and white Spirit creature tokens. Then if this spell was cast from anywhere other than your hand, put a +1/+1 counter on each Spirit you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Spirit22RedWhiteToken(), 2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
                CastFromNonHandSourceCondition.instance,
                "Then if this spell was cast from anywhere other than your hand, "
                        + "put a +1/+1 counter on each Spirit you control"
        ));

        // Flashback {4}{W}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{W}{W}")));
    }

    private AntiquitiesOnTheLoose(final AntiquitiesOnTheLoose card) {
        super(card);
    }

    @Override
    public AntiquitiesOnTheLoose copy() {
        return new AntiquitiesOnTheLoose(this);
    }
}

enum CastFromNonHandSourceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(zone -> !Zone.HAND.match(zone))
                .orElse(false);
    }

    @Override
    public String toString() {
        return "this spell was cast from anywhere other than your hand";
    }
}
