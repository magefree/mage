package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheTraveler extends CardImpl {

    public TheTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Reach, vigilance
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Strange new worlds -- The Traveler's power is equal to the number of differently named lands you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new SetBasePowerSourceEffect(StrangeNewWorldsValue.instance)
                .setText("{this}'s power is equal to the number of differently named lands you control")
        ).setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS).addHint(StrangeNewWorldsHint.instance));
    }

    private TheTraveler(final TheTraveler card) {
        super(card);
    }

    @Override
    public TheTraveler copy() {
        return new TheTraveler(this);
    }
}
