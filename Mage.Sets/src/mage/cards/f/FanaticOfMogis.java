package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FanaticOfMogis extends CardImpl {

    public FanaticOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Fanatic of Mogis enters the battlefield, it deals damage to each opponent equal to your devotion to red.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(Outcome.Damage, DevotionCount.R, TargetController.OPPONENT)
                        .setText("it deals damage to each opponent equal to your devotion to red."), false
        ).addHint(DevotionCount.R.getHint()));
    }

    private FanaticOfMogis(final FanaticOfMogis card) {
        super(card);
    }

    @Override
    public FanaticOfMogis copy() {
        return new FanaticOfMogis(this);
    }
}
