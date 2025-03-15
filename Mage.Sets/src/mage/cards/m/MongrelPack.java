package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.GreenDogToken;

import java.util.UUID;

/**
 * @author North
 */
public final class MongrelPack extends CardImpl {

    public MongrelPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Mongrel Pack dies during combat, create four 1/1 green Hound creature tokens.
        this.addAbility(new MongrelPackAbility());
    }

    private MongrelPack(final MongrelPack card) {
        super(card);
    }

    @Override
    public MongrelPack copy() {
        return new MongrelPack(this);
    }
}

class MongrelPackAbility extends DiesSourceTriggeredAbility {

    MongrelPackAbility() {
        super(new CreateTokenEffect(new GreenDogToken(), 4));
        setTriggerPhrase("When {this} dies during combat, ");
    }

    private MongrelPackAbility(MongrelPackAbility ability) {
        super(ability);
    }

    @Override
    public MongrelPackAbility copy() {
        return new MongrelPackAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getTurnPhaseType() == TurnPhase.COMBAT && super.checkTrigger(event, game);
    }
}
