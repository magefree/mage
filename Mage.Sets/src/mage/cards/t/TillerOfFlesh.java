package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TillerOfFlesh extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets one or more permanents");

    static {
        filter.add(TillerOfFleshPredicate.instance);
    }

    public TillerOfFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a spell that targets one or more permanents, incubate 2.
        this.addAbility(new SpellCastControllerTriggeredAbility(new IncubateEffect(2), filter, false));
    }

    private TillerOfFlesh(final TillerOfFlesh card) {
        super(card);
    }

    @Override
    public TillerOfFlesh copy() {
        return new TillerOfFlesh(this);
    }
}

enum TillerOfFleshPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .anyMatch(Objects::nonNull);
    }
}