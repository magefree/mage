package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class OildeepGearhulk extends CardImpl {

    public OildeepGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}{U}{B}{B}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // When this creature enters, look at target player's hand. You may choose a card from it. If you do, that player discards that card, then draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new OildeepGearhulkEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private OildeepGearhulk(final OildeepGearhulk card) {
        super(card);
    }

    @Override
    public OildeepGearhulk copy() {
        return new OildeepGearhulk(this);
    }
}

class OildeepGearhulkEffect extends OneShotEffect {

    public OildeepGearhulkEffect() {
        super(Outcome.Benefit);
        staticText = "look at target player's hand. You may choose a card from it. If you do, that player discards that card, then draws a card";
    }

    public OildeepGearhulkEffect(final OildeepGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public OildeepGearhulkEffect copy() {
        return new OildeepGearhulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller == null || targetPlayer == null) {
            return false;
        }

        controller.lookAtCards(targetPlayer.getName() + " Hand", targetPlayer.getHand(), game);
        TargetCard chosenCard = new TargetCard(0, 1, Zone.HAND, new FilterCard("card to discard"));
        if (!controller.choose(Outcome.Discard, targetPlayer.getHand(), chosenCard, source, game)) {
            return false;
        }
        Card card = game.getCard(chosenCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        targetPlayer.discard(card, false, source, game);
        targetPlayer.drawCards(1, source, game);
        return true;
    }
}
