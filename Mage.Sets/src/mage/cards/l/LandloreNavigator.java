package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.MapToken;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.UUID;

public final class LandloreNavigator extends CardImpl {

    public LandloreNavigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new MapToken())));
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.YOU,
                new ConjureCardEffect("Thieving Magpie", Zone.BATTLEFIELD, 1), false,
                LandloreNavigatorCondition.instance), new PermanentsEnteredBattlefieldWatcher());
    }

    private LandloreNavigator(final LandloreNavigator card) {
        super(card);
    }

    @Override
    public LandloreNavigator copy() {
        return new LandloreNavigator(this);
    }
}

enum LandloreNavigatorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        return watcher != null && watcher.getThisTurnEnteringPermanents(source.getControllerId()).stream()
                .filter(permanent -> permanent.isArtifact(game)).count() >= 2;
    }

    @Override
    public String toString() {
        return "two or more artifacts entered the battlefield under your control this turn";
    }
}
