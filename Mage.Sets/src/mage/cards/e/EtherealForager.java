package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtherealForager extends CardImpl {

    public EtherealForager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Delve
        this.addAbility(new DelveAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ethereal Forager attacks, you may return an instant or sorcery card exiled with Ethereal Forager to its owner's hand.
        this.addAbility(new AttacksTriggeredAbility(new EtherealForagerEffect(), true));
    }

    private EtherealForager(final EtherealForager card) {
        super(card);
    }

    @Override
    public EtherealForager copy() {
        return new EtherealForager(this);
    }
}

class EtherealForagerEffect extends OneShotEffect {

    EtherealForagerEffect() {
        super(Outcome.Benefit);
        staticText = "return an instant or sorcery card exiled with {this} to its owner's hand";
    }

    private EtherealForagerEffect(final EtherealForagerEffect effect) {
        super(effect);
    }

    @Override
    public EtherealForagerEffect copy() {
        return new EtherealForagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        String keyString = CardUtil.getCardZoneString("delvedCards", source.getSourceId(), game, true);
        Cards delvedCards = (Cards) game.getState().getValue(keyString);
        if (delvedCards == null || delvedCards.count(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY, game) < 1) {
            return false;
        }
        TargetCard targetCard = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, null, true);
        ;
        player.choose(Outcome.DrawCard, delvedCards, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null || !player.moveCards(card, Zone.HAND, source, game)) {
            return false;
        }
        delvedCards.remove(card);
        return true;
    }
}
