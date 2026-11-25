package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAloneSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PetSehlat extends CardImpl {

    public PetSehlat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // This creature can't attack alone.
        this.addAbility(new SimpleStaticAbility(new CantAttackAloneSourceEffect()));
    }

    private PetSehlat(final PetSehlat card) {
        super(card);
    }

    @Override
    public PetSehlat copy() {
        return new PetSehlat(this);
    }
}
