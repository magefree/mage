package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlacialDragonhunt extends CardImpl {

    public GlacialDragonhunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{R}");

        // Draw a card, then you may discard a card. When you discard a nonland card this way, Glacial Dragonhunt deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new GlacialDragonhuntEffect());

        // Harmonize {4}{U}{R}
        this.addAbility(new HarmonizeAbility(this, "{4}{U}{R}"));
    }

    private GlacialDragonhunt(final GlacialDragonhunt card) {
        super(card);
    }

    @Override
    public GlacialDragonhunt copy() {
        return new GlacialDragonhunt(this);
    }
}

class GlacialDragonhuntEffect extends OneShotEffect {

    GlacialDragonhuntEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may discard a card. When you discard a nonland card this way, " +
                "{this} deals 3 damage to target creature";
    }

    private GlacialDragonhuntEffect(final GlacialDragonhuntEffect effect) {
        super(effect);
    }

    @Override
    public GlacialDragonhuntEffect copy() {
        return new GlacialDragonhuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()
                || player
                .discard(0, 1, true, source, game)
                .count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(3), false);
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
