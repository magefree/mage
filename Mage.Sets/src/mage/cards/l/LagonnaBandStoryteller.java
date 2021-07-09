package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public final class LagonnaBandStoryteller extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment card from your graveyard");

    public LagonnaBandStoryteller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Lagonna-Band Storyteller enters the battlefield, you may put target 
        // enchantment card from your graveyard on top of your library. 
        // If you do, you gain life equal to its converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LagonnaBandStorytellerEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private LagonnaBandStoryteller(final LagonnaBandStoryteller card) {
        super(card);
    }

    @Override
    public LagonnaBandStoryteller copy() {
        return new LagonnaBandStoryteller(this);
    }
}

class LagonnaBandStorytellerEffect extends OneShotEffect {

    LagonnaBandStorytellerEffect() {
        super(Outcome.Benefit);
        staticText = "put target enchantment card from your graveyard on top of your library. "
                + "If you do, you gain life equal to its mana value.";
    }

    private LagonnaBandStorytellerEffect(final LagonnaBandStorytellerEffect effect) {
        super(effect);
    }

    @Override
    public LagonnaBandStorytellerEffect copy() {
        return new LagonnaBandStorytellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null
                || card == null
                || !card.isEnchantment(game)
                || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        int cmc = card.getManaValue();
        if (controller.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false)) {
            controller.gainLife(cmc, game, source);
            return true;
        }
        return false;
    }
}
