package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TheMasterOfLakeTown extends CardImpl {

    public TheMasterOfLakeTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a player loses life, that player mills that many cards.
        this.addAbility(new LoseLifeTriggeredAbility(
            new MillCardsTargetEffect(SavedLifeLossValue.MANY),
            TargetController.ANY, false, true
        ));

        // When The Master of Lake-town dies, draw a card for each graveyard with seven or more cards in it.
        this.addAbility(new DiesSourceTriggeredAbility(new TheMasterOfLakeTownEffect()));
    }

    private TheMasterOfLakeTown(final TheMasterOfLakeTown card) {
        super(card);
    }

    @Override
    public TheMasterOfLakeTown copy() {
        return new TheMasterOfLakeTown(this);
    }
}

class TheMasterOfLakeTownEffect extends OneShotEffect {

    TheMasterOfLakeTownEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card for each graveyard with seven or more cards in it";
    }

    private TheMasterOfLakeTownEffect(final TheMasterOfLakeTownEffect effect) {
        super(effect);
    }

    @Override
    public TheMasterOfLakeTownEffect copy() {
        return new TheMasterOfLakeTownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player.getGraveyard().size() < 7) {
                continue;
            }
            count++;
        }
        if (count > 0) {
            controller.drawCards(count, source, game);
        }
        return true;
    }
}
