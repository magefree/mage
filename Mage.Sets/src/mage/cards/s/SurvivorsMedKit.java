package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurvivorsMedKit extends CardImpl {

    public SurvivorsMedKit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}: Choose one that hasn't been chosen --
        // * Stimpak -- Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.withFirstModeFlavorWord("Stimpak");
        ability.getModes().setLimitUsageByOnce(true);

        // * Fancy Lads Snack Cakes -- Create a Food token.
        ability.addMode(new Mode(new CreateTokenEffect(new FoodToken())).withFlavorWord("Fancy Lads Snack Cakes"));

        // * RadAway -- Target player loses all rad counters. Sacrifice Survivor's Med Kit.
        ability.addMode(new Mode(new SurvivorsMedKitEffect())
                .addEffect(new SacrificeSourceEffect())
                .addTarget(new TargetPlayer())
                .withFlavorWord("RadAway"));
    }

    private SurvivorsMedKit(final SurvivorsMedKit card) {
        super(card);
    }

    @Override
    public SurvivorsMedKit copy() {
        return new SurvivorsMedKit(this);
    }
}

class SurvivorsMedKitEffect extends OneShotEffect {

    SurvivorsMedKitEffect() {
        super(Outcome.Benefit);
        staticText = "target player loses all rad counters";
    }

    private SurvivorsMedKitEffect(final SurvivorsMedKitEffect effect) {
        super(effect);
    }

    @Override
    public SurvivorsMedKitEffect copy() {
        return new SurvivorsMedKitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count = player.getCounters().getCount(CounterType.RAD);
        if (count > 0) {
            player.removeCounters("rad", count, source, game);
            return true;
        }
        return false;
    }
}
