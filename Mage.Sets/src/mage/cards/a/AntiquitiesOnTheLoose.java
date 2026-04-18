package mage.cards.a;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.Spirit22RedWhiteToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 *
 * @author muz
 */
public final class AntiquitiesOnTheLoose extends CardImpl {

    public AntiquitiesOnTheLoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Create two 2/2 red and white Spirit creature tokens. Then if this spell was cast from anywhere other than your hand, put a +1/+1 counter on each Spirit you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Spirit22RedWhiteToken(), 2));
        this.getSpellAbility().addEffect(new AntiquitiesOnTheLooseEffect());

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

class AntiquitiesOnTheLooseEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT, "Spirit you control");

    AntiquitiesOnTheLooseEffect() {
        super(Outcome.Benefit);
        staticText = "then if this spell was cast from anywhere other than your hand, "
        + "put two +1/+1 counters on each Spirit you control";
    }

    private AntiquitiesOnTheLooseEffect(final AntiquitiesOnTheLooseEffect effect) {
        super(effect);
    }

    @Override
    public AntiquitiesOnTheLooseEffect copy() {
        return new AntiquitiesOnTheLooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = Optional
                .ofNullable(source.getSourceObjectIfItStillExists(game))
                .filter(Spell.class::isInstance)
                .map(Spell.class::cast)
                .orElse(null);
        if (spell != null && !Zone.HAND.match(spell.getFromZone())) {
            new AddCountersAllEffect(CounterType.P1P1.createInstance(2), filter).apply(game, source);
        }

        return true;
    }
}
