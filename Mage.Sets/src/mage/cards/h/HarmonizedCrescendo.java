package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarmonizedCrescendo extends CardImpl {

    public HarmonizedCrescendo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Choose a creature type. Draw a card for each permanent you control of that type.
        this.getSpellAbility().addEffect(new HarmonizedCrescendoEffect());
    }

    private HarmonizedCrescendo(final HarmonizedCrescendo card) {
        super(card);
    }

    @Override
    public HarmonizedCrescendo copy() {
        return new HarmonizedCrescendo(this);
    }
}

class HarmonizedCrescendoEffect extends OneShotEffect {

    HarmonizedCrescendoEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Draw a card for each permanent you control of that type";
    }

    private HarmonizedCrescendoEffect(final HarmonizedCrescendoEffect effect) {
        super(effect);
    }

    @Override
    public HarmonizedCrescendoEffect copy() {
        return new HarmonizedCrescendoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceCreatureType(game, source);
        player.choose(outcome, choice, game);
        SubType subType = SubType.byDescription(choice.getChoice());
        if (subType == null) {
            return false;
        }
        game.informPlayers(CardUtil.getSourceLogName(game, source) + ": " + player.getLogName() + " chooses " + subType);
        int count = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT, source.getControllerId(), source, game)
                .stream()
                .filter(permanent -> permanent.hasSubtype(subType, game))
                .mapToInt(x -> 1)
                .sum();
        return count > 0 && player.drawCards(count, source, game) > 0;
    }
}
