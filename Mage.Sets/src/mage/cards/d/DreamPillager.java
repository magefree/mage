package mage.cards.d;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public final class DreamPillager extends CardImpl {

    public DreamPillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Dream Pillager deals combat damage to a player, exile that many cards from the top of your library. Until end of turn, you may cast nonland cards from among those exiled cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DreamPillagerEffect(),
                false, true));
    }

    private DreamPillager(final DreamPillager card) {
        super(card);
    }

    @Override
    public DreamPillager copy() {
        return new DreamPillager(this);
    }
}

class DreamPillagerEffect extends OneShotEffect {

    public DreamPillagerEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile that many cards from the top of your library. Until end of turn, you may cast spells from among those exiled cards";
    }

    public DreamPillagerEffect(final DreamPillagerEffect effect) {
        super(effect);
    }

    @Override
    public DreamPillagerEffect copy() {
        return new DreamPillagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
                if (!cards.isEmpty()) {
                    controller.moveCards(cards, Zone.EXILED, source, game);
                    Cards canBeCast = new CardsImpl();
                    for (Card card : cards) {
                        if (!card.isLand(game)) {
                            canBeCast.add(card);
                        }
                    }
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTargets(canBeCast, game));
                    game.addEffect(effect, source);
                }
                return true;
            }
            return true;
        }
        return false;
    }
}
