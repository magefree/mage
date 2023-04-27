package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SheoldredTheApocalypse extends CardImpl {

    public SheoldredTheApocalypse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you draw a card, you gain 2 life.
        this.addAbility(new DrawCardControllerTriggeredAbility(new GainLifeEffect(2), false));

        // Whenever an opponent draws a card, they lose 2 life.
        this.addAbility(new DrawCardOpponentTriggeredAbility(
                new LoseLifeTargetEffect(2).setText("they lose 2 life"), false, true
        ));
    }

    private SheoldredTheApocalypse(final SheoldredTheApocalypse card) {
        super(card);
    }

    @Override
    public SheoldredTheApocalypse copy() {
        return new SheoldredTheApocalypse(this);
    }
}
