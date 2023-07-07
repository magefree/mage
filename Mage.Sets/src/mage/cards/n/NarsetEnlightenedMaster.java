package mage.cards.n;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NarsetEnlightenedMaster extends CardImpl {

    public NarsetEnlightenedMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new NarsetEnlightenedMasterExileEffect(), false));

    }

    private NarsetEnlightenedMaster(final NarsetEnlightenedMaster card) {
        super(card);
    }

    @Override
    public NarsetEnlightenedMaster copy() {
        return new NarsetEnlightenedMaster(this);
    }
}

class NarsetEnlightenedMasterExileEffect extends OneShotEffect {

    public NarsetEnlightenedMasterExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with {this} this turn without paying their mana costs";
    }

    public NarsetEnlightenedMasterExileEffect(final NarsetEnlightenedMasterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            Set<Card> cards = player.getLibrary().getTopCards(game, 4);
            player.moveCards(cards, Zone.EXILED, source, game);
            for (Card card : cards) {
                // TODO: this is not quite right with mdfc creature/non-creature.
                if (game.getState().getZone(card.getId()) == Zone.EXILED
                        && !card.isCreature(game)) {
                    CardUtil.makeCardCastable(game, source, card, Duration.EndOfTurn,
                        CardUtil.CastManaAdjustment.WITHOUT_PAYING_MANA_COST);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public NarsetEnlightenedMasterExileEffect copy() {
        return new NarsetEnlightenedMasterExileEffect(this);
    }
}