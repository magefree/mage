package mage.cards.e;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{G}{G}", "Dissonant Wave", "{2}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Dissonant Wave
        // Counter target activated or triggered ability from a noncreature source.
        this.getSpellCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility(filter));
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