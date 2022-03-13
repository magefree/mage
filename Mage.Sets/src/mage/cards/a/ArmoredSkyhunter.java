package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmoredSkyhunter extends CardImpl {

    public ArmoredSkyhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Armored Skyhunter attacks, look at the top six cards of your library. You may put an Aura or Equipment card from among them onto the battlefield. If an Equipment is put onto the battlefield this way, you may attach it to a creature you control. Put the rest of those cards on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new ArmoredSkyhunterEffect(), false));
    }

    private ArmoredSkyhunter(final ArmoredSkyhunter card) {
        super(card);
    }

    @Override
    public ArmoredSkyhunter copy() {
        return new ArmoredSkyhunter(this);
    }
}

class ArmoredSkyhunterEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    ArmoredSkyhunterEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. You may put an " +
                "Aura or Equipment card from among them onto the battlefield. " +
                "If an Equipment is put onto the battlefield this way, you may attach it to a creature you control. " +
                "Put the rest of those cards on the bottom of your library in a random order";
    }

    private ArmoredSkyhunterEffect(final ArmoredSkyhunterEffect effect) {
        super(effect);
    }

    @Override
    public ArmoredSkyhunterEffect copy() {
        return new ArmoredSkyhunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCardInLibrary targetCard = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, targetCard, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        Permanent equipment = game.getPermanent(card.getId());
        if (equipment == null || !equipment.hasSubtype(SubType.EQUIPMENT, game)) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        TargetPermanent targetPermanent = new TargetControlledCreaturePermanent(0, 1);
        targetCard.setNotTarget(true);
        player.choose(outcome, targetPermanent, source, game);
        Permanent permanent = game.getPermanent(targetPermanent.getFirstTarget());
        if (permanent != null) {
            permanent.addAttachment(equipment.getId(), source, game);
        }
        return player.putCardsOnBottomOfLibrary(cards, game, source, false);
    }
}
