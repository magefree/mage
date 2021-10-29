package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WearyPrisoner extends CardImpl {

    public WearyPrisoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.w.WrathfulJailbreaker.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private WearyPrisoner(final WearyPrisoner card) {
        super(card);
    }

    @Override
    public WearyPrisoner copy() {
        return new WearyPrisoner(this);
    }
}
