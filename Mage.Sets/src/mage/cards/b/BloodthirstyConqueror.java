package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodthirstyConqueror extends CardImpl {

    public BloodthirstyConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever an opponent loses life, you gain that much life.
        this.addAbility(new LoseLifeTriggeredAbility(new GainLifeEffect(SavedLifeLossValue.MUCH),
                TargetController.OPPONENT, false, false));
    }

    private BloodthirstyConqueror(final BloodthirstyConqueror card) {
        super(card);
    }

    @Override
    public BloodthirstyConqueror copy() {
        return new BloodthirstyConqueror(this);
    }
}
