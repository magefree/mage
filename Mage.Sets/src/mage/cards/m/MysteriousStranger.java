package mage.cards.m;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteriousStranger extends CardImpl {

    public MysteriousStranger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Mysterious Stranger enters the battlefield, for each graveyard with an instant or sorcery card in it, exile target instant or sorcery card from that graveyard. If two or more cards are exiled this way, choose one of them at random and copy it. You may cast the copy without paying its mana cost.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MysteriousStrangerEffect())
                .setTargetAdjuster(MysteriousStrangerAdjuster.instance));
    }

    private MysteriousStranger(final MysteriousStranger card) {
        super(card);
    }

    @Override
    public MysteriousStranger copy() {
        return new MysteriousStranger(this);
    }
}

enum MysteriousStrangerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game) < 1) {
                continue;
            }
            FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card (owned by " +
                    (ability.isControlledBy(playerId) ? "you" : player.getLogName()) + ')');
            filter.add(new OwnerIdPredicate(playerId));
            ability.addTarget(new TargetCardInGraveyard(filter));
        }
    }
}

class MysteriousStrangerEffect extends OneShotEffect {

    MysteriousStrangerEffect() {
        super(Outcome.Benefit);
        staticText = "for each graveyard with an instant or sorcery card in it, " +
                "exile target instant or sorcery card from that graveyard. " +
                "If two or more cards are exiled this way, choose one of them at random and copy it. " +
                "You may cast the copy without paying its mana cost";
        this.setTargetPointer(new EachTargetPointer());
    }

    private MysteriousStrangerEffect(final MysteriousStrangerEffect effect) {
        super(effect);
    }

    @Override
    public MysteriousStrangerEffect copy() {
        return new MysteriousStrangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game);
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        if (cards.size() < 2) {
            return true;
        }
        Card card = cards.getRandom(game);
        if (card == null) {
            return true;
        }
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        if (!player.chooseUse(Outcome.PlayForFree, "Cast a copy of " +
                card.getLogName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
