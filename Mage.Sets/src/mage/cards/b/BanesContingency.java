package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BanesContingency extends CardImpl {

    public BanesContingency(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. If that spell targets a commander you control, instead counter that spell, scry 2, then draw a card.
        this.getSpellAbility().addEffect(new BanesContingencyEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private BanesContingency(final BanesContingency card) {
        super(card);
    }

    @Override
    public BanesContingency copy() {
        return new BanesContingency(this);
    }
}

class BanesContingencyEffect extends OneShotEffect {

    BanesContingencyEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. If that spell targets a commander you control, " +
                "instead counter that spell, scry 2, then draw a card";
    }

    private BanesContingencyEffect(final BanesContingencyEffect effect) {
        super(effect);
    }

    @Override
    public BanesContingencyEffect copy() {
        return new BanesContingencyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        boolean flag = spell
                .getSpellAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isControlledBy(source.getControllerId()))
                .anyMatch(permanent -> CommanderPredicate.instance.apply(permanent, game));
        game.getStack().counter(spell.getId(), source, game);
        if (!flag) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.scry(2, source, game);
            player.drawCards(1, source, game);
        }
        return true;
    }
}
