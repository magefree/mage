
package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;


/**
 * @author Svyatoslav28
 */

public final class JukaiLiberator extends CardImpl {

    public JukaiLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ninjutsu {1}{G}
        this.addAbility(new NinjutsuAbility("{1}{G}"));

        // Whenever Jukai Liberator deals combat damage to a player, choose land or nonland. Seek a permanent card of the chosen type.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new JukaiLiberatorEffect());
        this.addAbility(ability);
    }

    private JukaiLiberator(final JukaiLiberator card) {
        super(card);
    }

    @Override
    public JukaiLiberator copy() {
        return new JukaiLiberator(this);
    }
}

class JukaiLiberatorEffect extends OneShotEffect {

    JukaiLiberatorEffect() {
        super(Outcome.Benefit);
        staticText = "choose land or nonland." +
                " Seek a permanent card of the chosen kind.";
    }

    private JukaiLiberatorEffect(final JukaiLiberatorEffect effect) {
        super(effect);
    }

    @Override
    public JukaiLiberatorEffect copy() {
        return new JukaiLiberatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean land = player.chooseUse(
                Outcome.Neutral, "Choose land or nonland", null,
                "Land", "Nonland", source, game
        );
        if (land) {
            player.seekCard(StaticFilters.FILTER_CARD_LAND, source, game);
        } else {
            player.seekCard(StaticFilters.FILTER_CARD_NON_LAND, source, game);
        }
        return true;
    }
}


