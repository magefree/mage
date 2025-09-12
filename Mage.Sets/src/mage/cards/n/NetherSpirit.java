package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
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

import java.util.UUID;

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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                TargetController.YOU, new ReturnSourceFromGraveyardToBattlefieldEffect().setText("return this card to the battlefield"),
                true).withInterveningIf(NetherSpiritCondition.instance));
    }

    private NetherSpirit(final NetherSpirit card) {
        super(card);
    }

    @Override
    public NetherSpirit copy() {
        return new NetherSpirit(this);
    }
}

enum NetherSpiritCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        return player != null && card != null && player.getGraveyard().contains(card.getId())
                && player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) == 1;
    }

    @Override
    public String toString() {
        return "this card is the only creature card in your graveyard";
    }
}
