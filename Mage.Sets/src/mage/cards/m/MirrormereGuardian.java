package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrormereGuardian extends CardImpl {

    public MirrormereGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Mirrormere Guardian dies, the Ring tempts you.
        this.addAbility(new DiesSourceTriggeredAbility(new TheRingTemptsYouEffect()));
    }

    private MirrormereGuardian(final MirrormereGuardian card) {
        super(card);
    }

    @Override
    public MirrormereGuardian copy() {
        return new MirrormereGuardian(this);
    }
}
