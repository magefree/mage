
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class HeartwoodStoryteller extends CardImpl {

    public HeartwoodStoryteller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a player casts a noncreature spell, each of that player's opponents may draw a card.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new HeartwoodStorytellerEffect(),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE,
                false, SetTargetPointer.PLAYER
        ));
    }

    private HeartwoodStoryteller(final HeartwoodStoryteller card) {
        super(card);
    }

    @Override
    public HeartwoodStoryteller copy() {
        return new HeartwoodStoryteller(this);
    }
}

class HeartwoodStorytellerEffect extends OneShotEffect {

    HeartwoodStorytellerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "each of that player's opponents may draw a card";
    }

    private HeartwoodStorytellerEffect(final HeartwoodStorytellerEffect effect) {
        super(effect);
    }

    @Override
    public HeartwoodStorytellerEffect copy() {
        return new HeartwoodStorytellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(this.getTargetPointer().getFirst(game, source))) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(outcome, "Draw a card?", source, game)) {
                    player.drawCards(1, source, game);
                }
            }
        }
        return true;
    }
}
