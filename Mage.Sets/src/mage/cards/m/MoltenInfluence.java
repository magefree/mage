package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author cbt33, LevelX2 (Quash)
 */
public final class MoltenInfluence extends CardImpl {

    public MoltenInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Counter target instant or sorcery spell unless its controller has Molten Influence deal 4 damage to them.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().addEffect(new MoltenInfluenceEffect());

    }

    private MoltenInfluence(final MoltenInfluence card) {
        super(card);
    }

    @Override
    public MoltenInfluence copy() {
        return new MoltenInfluence(this);
    }
}

class MoltenInfluenceEffect extends OneShotEffect {

    public MoltenInfluenceEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target instant or sorcery spell unless its controller has {this} deal 4 damage to them";
    }

    private MoltenInfluenceEffect(final MoltenInfluenceEffect effect) {
        super(effect);
    }

    @Override
    public MoltenInfluenceEffect copy() {
        return new MoltenInfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            String message = "Have Molten Influence do 4 damage to you?";
            if (player != null && player.chooseUse(Outcome.Damage, message, source, game)) {
                player.damage(4, source.getSourceId(), source, game);
                return true;
            } else {
                return game.getStack().counter(source.getFirstTarget(), source, game);
            }
        }
        return false;
    }
}
