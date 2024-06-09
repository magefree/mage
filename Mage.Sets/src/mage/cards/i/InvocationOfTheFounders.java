package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvocationOfTheFounders extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand");

    static {
        filter.add(new CastFromZonePredicate(Zone.HAND));
    }

    public InvocationOfTheFounders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setBlue(true);
        this.nightCard = true;

        // Whenever you cast an instant or sorcery spell from your hand, you may copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new InvocationOfTheFoundersEffect(), filter, true
        ));
    }

    private InvocationOfTheFounders(final InvocationOfTheFounders card) {
        super(card);
    }

    @Override
    public InvocationOfTheFounders copy() {
        return new InvocationOfTheFounders(this);
    }
}

class InvocationOfTheFoundersEffect extends OneShotEffect {

    InvocationOfTheFoundersEffect() {
        super(Outcome.Benefit);
        staticText = "copy that spell. You may choose new targets for the copy";
    }

    private InvocationOfTheFoundersEffect(final InvocationOfTheFoundersEffect effect) {
        super(effect);
    }

    @Override
    public InvocationOfTheFoundersEffect copy() {
        return new InvocationOfTheFoundersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
            return true;
        }
        return false;
    }
}
