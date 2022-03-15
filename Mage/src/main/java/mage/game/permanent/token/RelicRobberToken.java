package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.Arrays;

public final class RelicRobberToken extends TokenImpl {

    public RelicRobberToken() {
        super("Goblin Construct Token", "0/1 colorless Goblin Construct artifact creature token with \"This creature can't block\" and \"At the beginning of your upkeep, this creature deals 1 damage to you.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield).setText("this creature can't block")
        ));
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(
                1, "this creature"
        ), TargetController.YOU, false));

        availableImageSetCodes = Arrays.asList("ZNR");
    }

    public RelicRobberToken(final RelicRobberToken token) {
        super(token);
    }

    public RelicRobberToken copy() {
        return new RelicRobberToken(this);
    }
}
