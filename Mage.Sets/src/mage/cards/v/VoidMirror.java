package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoidMirror extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell");

    static {
        filter.add(VoidMirrorPredicate.instance);
    }

    public VoidMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a player casts a spell, if no colored mana was spent to cast it, counter that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new VoidMirrorEffect(), filter, false));
    }

    private VoidMirror(final VoidMirror card) {
        super(card);
    }

    @Override
    public VoidMirror copy() {
        return new VoidMirror(this);
    }
}

enum VoidMirrorPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return ((Spell) input).getSpellAbility().getManaCostsToPay().getUsedManaToPay().countColored() < 1;
    }
}

class VoidMirrorEffect extends OneShotEffect {

    VoidMirrorEffect() {
        super(Outcome.Benefit);
        staticText = "if no colored mana was spent to cast it, counter that spell";
    }

    private VoidMirrorEffect(final VoidMirrorEffect effect) {
        super(effect);
    }

    @Override
    public VoidMirrorEffect copy() {
        return new VoidMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            game.getStack().counter(spell.getId(), source, game);
            return true;
        }
        return false;
    }
}
