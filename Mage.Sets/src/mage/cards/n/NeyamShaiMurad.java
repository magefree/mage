package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author Xanderhall
 */
public final class NeyamShaiMurad extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from their graveyard");

    public NeyamShaiMurad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Rogue Trader -- Whenever Neyam Shai Murad deals combat damage to a player, you may have that player return target permanent card from their graveyard to their hand.
        // If you do, that player chooses a permanent card in your graveyard, then you put it onto the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new NeyamShaiMuradEffect(), true, true)
                .withFlavorWord("Rogue Trader");
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private NeyamShaiMurad(final NeyamShaiMurad card) {
        super(card);
    }

    @Override
    public NeyamShaiMurad copy() {
        return new NeyamShaiMurad(this);
    }
}

class NeyamShaiMuradEffect extends OneShotEffect {
    
    NeyamShaiMuradEffect() {
        super(Outcome.Benefit);
        staticText = "you may have that player return target permanent card from their graveyard to their hand. "
            + "If you do, that player chooses a permanent card in your graveyard, then you put it onto the battlefield under your control";
    }

    private NeyamShaiMuradEffect(final NeyamShaiMuradEffect effect) {
        super(effect);
    }

    @Override
    public NeyamShaiMuradEffect copy() {
        return new NeyamShaiMuradEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object playerId = getValue("damagedPlayer");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !(playerId instanceof UUID)) {
            return false;
        }

        Player player = game.getPlayer((UUID) playerId);
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game); //verify the target card is still in the graveyard
        if (player == null || cards.isEmpty() || !player.moveCards(cards, Zone.HAND, source, game)) {
            return false;
        }

        Cards controllerGraveyard = controller.getGraveyard();
        FilterCard filter = new FilterPermanentCard("a permanent card in your graveyard");
        filter.add(new OwnerIdPredicate(source.getControllerId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(1, 1, filter, true);
        if (!target.choose(Outcome.PutCreatureInPlay, player.getId(), source, game)) {
            return false;
        }
        return controllerGraveyard.contains(target.getFirstTarget()) && controller.moveCards(controllerGraveyard.get(target.getFirstTarget(), game), Zone.BATTLEFIELD, source, game);
    }
}
