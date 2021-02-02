package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class NetherSpirit extends CardImpl {

    public NetherSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, if Nether Spirit is the only creature card in your graveyard, you may return Nether Spirit to the battlefield.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), TargetController.YOU, true);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new NetherSpiritCondition(), "At the beginning of your upkeep, if {this} is the only creature card in your graveyard, you may return {this} to the battlefield."));
    }

    private NetherSpirit(final NetherSpirit card) {
        super(card);
    }

    @Override
    public NetherSpirit copy() {
        return new NetherSpirit(this);
    }
}

class NetherSpiritCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                return player.getGraveyard().contains(card.getId()) && player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) == 1;
            }
        }
        return false;
    }
}
