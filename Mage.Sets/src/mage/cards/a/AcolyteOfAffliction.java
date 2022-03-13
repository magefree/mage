package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcolyteOfAffliction extends CardImpl {

    public AcolyteOfAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Acolyte of Affliction enters the battlefield, put the top two cards of your library into your graveyard, then you may return a permanent card from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AcolyteOfAfflictionEffect()));
    }

    private AcolyteOfAffliction(final AcolyteOfAffliction card) {
        super(card);
    }

    @Override
    public AcolyteOfAffliction copy() {
        return new AcolyteOfAffliction(this);
    }
}

class AcolyteOfAfflictionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    AcolyteOfAfflictionEffect() {
        super(Outcome.Benefit);
        staticText = "mill two cards, then you may return a permanent card from your graveyard to your hand.";
    }

    private AcolyteOfAfflictionEffect(final AcolyteOfAfflictionEffect effect) {
        super(effect);
    }

    @Override
    public AcolyteOfAfflictionEffect copy() {
        return new AcolyteOfAfflictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.moveCards(player.getLibrary().getTopCards(game, 2), Zone.GRAVEYARD, source, game);
        TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
        if (!player.choose(Outcome.ReturnToHand, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}