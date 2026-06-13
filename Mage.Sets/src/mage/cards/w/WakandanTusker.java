package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WakandanTusker extends CardImpl {

    public WakandanTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever this creature becomes tapped, you gain 1 life and scry 1.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new GainLifeEffect(1));
        ability.addEffect(new ScryEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private WakandanTusker(final WakandanTusker card) {
        super(card);
    }

    @Override
    public WakandanTusker copy() {
        return new WakandanTusker(this);
    }
}
