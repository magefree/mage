
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class SpellCrumple extends CardImpl {

    public SpellCrumple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell. If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard. Put Spell Crumple on the bottom of its owner's library.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new SpellCrumpleCounterEffect());
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    private SpellCrumple(final SpellCrumple card) {
        super(card);
    }

    @Override
    public SpellCrumple copy() {
        return new SpellCrumple(this);
    }
}

class SpellCrumpleCounterEffect extends OneShotEffect {

    public SpellCrumpleCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard";
    }

    public SpellCrumpleCounterEffect(final SpellCrumpleCounterEffect effect) {
        super(effect);
    }

    @Override
    public SpellCrumpleCounterEffect copy() {
        return new SpellCrumpleCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return game.getStack().counter(targetPointer.getFirst(game, source), source, game, Zone.LIBRARY, false, ZoneDetail.BOTTOM);
        }
        return false;
    }
}
