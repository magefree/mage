
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.AssistAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class Skystreamer extends CardImpl {

    public Skystreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Assist
        this.addAbility(new AssistAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Skystreamer enters the battlefield, target player gains 4 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeTargetEffect(4), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Skystreamer(final Skystreamer card) {
        super(card);
    }

    @Override
    public Skystreamer copy() {
        return new Skystreamer(this);
    }
}
