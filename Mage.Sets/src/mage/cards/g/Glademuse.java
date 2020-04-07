package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Glademuse extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell, if it's not their turn");

    static {
        filter.add(GlademusePredicate.instance);
    }

    public Glademuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever a player casts a spell, if it's not their turn, that player draws a card.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DrawCardTargetEffect(1).setText("that player draws a card"),
                filter, false, SetTargetPointer.PLAYER
        ));
    }

    private Glademuse(final Glademuse card) {
        super(card);
    }

    @Override
    public Glademuse copy() {
        return new Glademuse(this);
    }
}

enum GlademusePredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject spell, Game game) {
        return spell != null && !spell.getControllerId().equals(game.getActivePlayerId());
    }
}
