
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EntropicSpecter extends CardImpl {

    public EntropicSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.SPECTER);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Entropic Specter enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));

        // Entropic Specter's power and toughness are each equal to the number of cards in the chosen player's hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                // back to the graveyard or if the chosen player left the gane it's again a 0/0
                new SetBasePowerToughnessSourceEffect(CardsInTargetPlayerHandCount.instance, Duration.WhileOnBattlefield, SubLayer.CharacteristicDefining_7a)));

        // Whenever Entropic Specter deals damage to a player, that player discards a card.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1, false), false, true));
    }

    private EntropicSpecter(final EntropicSpecter card) {
        super(card);
    }

    @Override
    public EntropicSpecter copy() {
        return new EntropicSpecter(this);
    }
}

enum CardsInTargetPlayerHandCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                return chosenPlayer.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "cards in the chosen player's hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
