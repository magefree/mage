package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class Desertion extends CardImpl {

    public Desertion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell. If an artifact or creature spell is countered this way,
        // put that card onto the battlefield under your control instead of into its owner's graveyard.
        this.getSpellAbility().addEffect(new DesertionEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Desertion(final Desertion card) {
        super(card);
    }

    @Override
    public Desertion copy() {
        return new Desertion(this);
    }
}

class DesertionEffect extends OneShotEffect {

    public DesertionEffect() {
        super(Outcome.Detriment);
        staticText = "counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard";
    }

    private DesertionEffect(final DesertionEffect effect) {
        super(effect);
    }

    @Override
    public DesertionEffect copy() {
        return new DesertionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell == null) {
            return false;
        }
        return game.getStack().counter(spell.getId(), source, game,
                spell.isArtifact() || spell.isCreature() ? PutCards.BATTLEFIELD : PutCards.GRAVEYARD
        );
    }
}
