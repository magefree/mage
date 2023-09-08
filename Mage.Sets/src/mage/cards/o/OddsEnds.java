
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;

import java.util.UUID;

public final class OddsEnds extends SplitCard {

    public OddsEnds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}", "{3}{R}{W}", SpellAbilityType.SPLIT);

        // Odds
        // Flip a coin. If it comes up heads, counter target instant or sorcery spell. If it comes up tails, copy that spell and you may choose new targets for the copy.
        getLeftHalfCard().getSpellAbility().addEffect(new OddsEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));

        // Ends
        // Target player sacrifices two attacking creatures.
        getRightHalfCard().getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_ATTACKING_CREATURES, 2, "Target player"));
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());

    }

    private OddsEnds(final OddsEnds card) {
        super(card);
    }

    @Override
    public OddsEnds copy() {
        return new OddsEnds(this);
    }
}

class OddsEffect extends OneShotEffect {

    public OddsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Flip a coin. If it comes up heads, counter target instant or sorcery spell. If it comes up tails, copy that spell and you may choose new targets for the copy";
    }

    private OddsEffect(final OddsEffect effect) {
        super(effect);
    }

    @Override
    public OddsEffect copy() {
        return new OddsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(source, game, false)) {
                game.informPlayers("Odds: Spell countered");
                return game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);

            } else {
                game.informPlayers("Odds: Spell will be copied");
                return new CopyTargetSpellEffect().apply(game, source);
            }
        }
        return false;
    }
}
