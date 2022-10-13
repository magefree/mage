package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class DiscerningTaste extends CardImpl {

    public DiscerningTaste(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard. You gain life equal to the greatest power among creature cards put into your graveyard this way.
        this.getSpellAbility().addEffect(new DiscerningTasteEffect());
    }

    private DiscerningTaste(final DiscerningTaste card) {
        super(card);
    }

    @Override
    public DiscerningTaste copy() {
        return new DiscerningTaste(this);
    }
}

class DiscerningTasteEffect extends LookLibraryAndPickControllerEffect {

    DiscerningTasteEffect() {
        super(4, 1, PutCards.HAND, PutCards.GRAVEYARD);
    }

    private DiscerningTasteEffect(final DiscerningTasteEffect effect) {
        super(effect);
    }

    @Override
    public DiscerningTasteEffect copy() {
        return new DiscerningTasteEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        super.actionWithPickedCards(game, source, player, pickedCards, otherCards);
        otherCards.retainZone(Zone.GRAVEYARD, game);
        int life = otherCards.getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        player.gainLife(life, game, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode).concat(". You gain life equal to the greatest power among creature cards put into your graveyard this way");
    }
}
