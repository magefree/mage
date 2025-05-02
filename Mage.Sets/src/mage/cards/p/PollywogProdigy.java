package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PollywogProdigy extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a noncreature spell with mana value less than {this}'s power");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(PollywogProdigyPredicate.instance);
    }

    public PollywogProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Whenever an opponent casts a noncreature spell with mana value less than Pollywog Prodigy's power, draw a card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private PollywogProdigy(final PollywogProdigy card) {
        super(card);
    }

    @Override
    public PollywogProdigy copy() {
        return new PollywogProdigy(this);
    }
}

enum PollywogProdigyPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(p -> input.getObject().getManaValue() < p.getValue())
                .orElse(false);
    }
}
