
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ChainOfAcid extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ChainOfAcid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Destroy target noncreature permanent. Then that permanent's controller may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfAcidEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ChainOfAcid(final ChainOfAcid card) {
        super(card);
    }

    @Override
    public ChainOfAcid copy() {
        return new ChainOfAcid(this);
    }
}

class ChainOfAcidEffect extends OneShotEffect {

    ChainOfAcidEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target noncreature permanent. Then that permanent's controller may copy this spell and may choose a new target for that copy.";
    }

    ChainOfAcidEffect(final ChainOfAcidEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfAcidEffect copy() {
        return new ChainOfAcidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = source.getFirstTarget();
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                Player affectedPlayer = game.getPlayer(permanent.getControllerId());
                if (affectedPlayer != null) {
                    Effect effect = new DestroyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(targetId, game));
                    effect.apply(game, source);
                    if (affectedPlayer.chooseUse(Outcome.Copy, "Copy the spell?", source, game)) {
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            spell.createCopyOnStack(game, source, affectedPlayer.getId(), true);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
