package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
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
 * @author Susucr
 */
public final class ScytheclawRaptor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell, if it's not their turn");

    static {
        filter.add(ScytheclawRaptorPredicate.instance);
    }

    public ScytheclawRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever a player casts a spell, if it's not their turn, Scytheclaw Raptor deals 4 damage to them.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DamageTargetEffect(4).setText("{this} deals 4 damage to them"),
                filter, false, SetTargetPointer.PLAYER
        ));
    }

    private ScytheclawRaptor(final ScytheclawRaptor card) {
        super(card);
    }

    @Override
    public ScytheclawRaptor copy() {
        return new ScytheclawRaptor(this);
    }
}

/**
 * This is a little weird of a setup, but it's working.
 * Inspired by {@link mage.cards.g.Glademuse}
 */
enum ScytheclawRaptorPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject spell, Game game) {
        return spell != null && !spell.getControllerId().equals(game.getActivePlayerId());
    }
}

