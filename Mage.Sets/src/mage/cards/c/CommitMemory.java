
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author fireshoes
 */
public final class CommitMemory extends SplitCard {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or nonland permanent");

    static {
        filter.setPermanentFilter(new FilterNonlandPermanent());
    }

    public CommitMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{3}{U}", "{4}{U}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Commit
        // Put target spell or nonland permanent into its owner's library second from the top.
        getLeftHalfCard().getSpellAbility().addEffect(new CommitEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpellOrPermanent(1, 1, filter, false));

        // Memory
        // Aftermath
        // Each player shuffles their hand and graveyard into their library, then draws seven cards.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        getRightHalfCard().getSpellAbility().addEffect(effect);
    }

    private CommitMemory(final CommitMemory card) {
        super(card);
    }

    @Override
    public CommitMemory copy() {
        return new CommitMemory(this);
    }
}

class CommitEffect extends OneShotEffect {

    public CommitEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target spell or nonland permanent into its owner's library second from the top";
    }

    private CommitEffect(final CommitEffect effect) {
        super(effect);
    }

    @Override
    public CommitEffect copy() {
        return new CommitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                return controller.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
            }
            Spell spell = game.getStack().getSpell(source.getFirstTarget());
            if (spell != null) {
                return controller.putCardOnTopXOfLibrary(spell, game, source, 2, true);
            }
        }
        return false;
    }
}
