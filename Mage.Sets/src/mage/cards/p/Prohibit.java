package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Prohibit extends CardImpl {

    public Prohibit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Counter target spell if its converted mana cost is 2 or less. 
        // If Prohibit was kicked, counter that spell if its 
        // converted mana cost is 4 or less instead.
        this.getSpellAbility().addEffect(new ProhibitEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Prohibit(final Prohibit card) {
        super(card);
    }

    @Override
    public Prohibit copy() {
        return new Prohibit(this);
    }
}

class ProhibitEffect extends OneShotEffect {

    ProhibitEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Counter target spell if its mana value "
                + "is 2 or less. If this spell was kicked, counter that "
                + "spell if its mana value is 4 or less instead.";
    }

    ProhibitEffect(final ProhibitEffect effect) {
        super(effect);
    }

    @Override
    public ProhibitEffect copy() {
        return new ProhibitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell targetSpell = game.getSpell(this.getTargetPointer().getFirst(game, source));
            if (targetSpell != null) {
                int cmc = targetSpell.getManaValue();
                if (cmc <= 2
                        || (KickedCondition.ONCE.apply(game, source) && cmc <= 4)) {
                    game.getStack().counter(targetSpell.getId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
