package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeapotSlinger extends CardImpl {

    public TeapotSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever you expend 4, Teapot Slinger deals 2 damage to each opponent.
        this.addAbility(new ExpendTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT),
                ExpendTriggeredAbility.Expend.FOUR
        ));
    }

    private TeapotSlinger(final TeapotSlinger card) {
        super(card);
    }

    @Override
    public TeapotSlinger copy() {
        return new TeapotSlinger(this);
    }
}
