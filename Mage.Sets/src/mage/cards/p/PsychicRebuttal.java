
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PsychicRebuttal extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell that targets you");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
        filter.add(new PsychicRebuttalPredicate());
    }

    public PsychicRebuttal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target instant or sorcery spell that targets you.
        this.getSpellAbility().addEffect(new PsychicRebuttalEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you may copy the spell countered this way. You may choose new targets for the copy.
    }

    private PsychicRebuttal(final PsychicRebuttal card) {
        super(card);
    }

    @Override
    public PsychicRebuttal copy() {
        return new PsychicRebuttal(this);
    }
}

class PsychicRebuttalEffect extends OneShotEffect {

    public PsychicRebuttalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target instant or sorcery spell that targets you."
                + "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you may copy the spell countered this way. You may choose new targets for the copy";
    }

    public PsychicRebuttalEffect(final PsychicRebuttalEffect effect) {
        super(effect);
    }

    @Override
    public PsychicRebuttalEffect copy() {
        return new PsychicRebuttalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            game.getStack().counter(spell.getId(), source, game);

            if (SpellMasteryCondition.instance.apply(game, source)
                    && controller.chooseUse(Outcome.PlayForFree, "Copy " + spell.getName() + " (you may choose new targets for the copy)?", source, game)) {
                spell.createCopyOnStack(game, source, source.getControllerId(), true);
            }

            return true;
        }
        return false;
    }
}

class PsychicRebuttalPredicate implements ObjectSourcePlayerPredicate<StackObject> {

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        UUID controllerId = input.getPlayerId();
        if (controllerId == null) {
            return false;
        }
        for (UUID modeId : input.getObject().getStackAbility().getModes().getSelectedModes()) {
            Mode mode = input.getObject().getStackAbility().getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    if (controllerId.equals(targetId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "spell that targets you";
    }
}
