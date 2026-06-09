package mage.cards.e;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmeraldDragon extends AdventureCard {

    private static final FilterStackObject filter
            = new FilterStackObject("activated or triggered ability from a noncreature source");

    static {
        filter.add(EmeraldDragonPredicate.instance);
    }

    public EmeraldDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{G}{G}",
                "Dissonant Wave",
                new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Emerald Dragon
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Dissonant Wave
        // Counter target activated or triggered ability from a noncreature source.
        this.getRightHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility(filter));

        finalizeCard();
    }

    private EmeraldDragon(final EmeraldDragon card) {
        super(card);
    }

    @Override
    public EmeraldDragon copy() {
        return new EmeraldDragon(this);
    }
}

enum EmeraldDragonPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof StackAbility
                && !((StackAbility) input).getSourceObject(game).isCreature(game);
    }
}