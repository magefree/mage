package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.CopiedStackObjectEvent;
import mage.game.events.CopyStackObjectEvent;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Fork extends CardImpl {


    public Fork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ForkEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));

    }

    private Fork(final Fork card) {
        super(card);
    }

    @Override
    public Fork copy() {
        return new Fork(this);
    }
}

class ForkEffect extends OneShotEffect {

    public ForkEffect() {
        super(Outcome.Copy);
        staticText = "Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy";
    }

    public ForkEffect(final ForkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null && controller != null) {
            // TODO: add support if multiple copies? See Twinning Staff
            GameEvent gameEvent = new CopyStackObjectEvent(source, spell, source.getControllerId(), 1);
            if (game.replaceEvent(gameEvent)) {
                return false;
            }
            Spell copy = spell.copySpell(game, source, source.getControllerId());
            copy.getColor(game).setRed(true);
            copy.setZone(Zone.STACK, game);
            game.getStack().push(copy);
            copy.chooseNewTargets(game, controller.getId());
            game.fireEvent(new CopiedStackObjectEvent(spell, copy, source.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public ForkEffect copy() {
        return new ForkEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Copy target ").append(mode.getTargets().get(0).getTargetName()).append(", except that the copy is red. You may choose new targets for the copy");
        return sb.toString();
    }
}
