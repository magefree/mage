package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HammerheadShark extends CardImpl {

    public HammerheadShark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SHARK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Hammerhead Shark can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));
    }

    private HammerheadShark(final HammerheadShark card) {
        super(card);
    }

    @Override
    public HammerheadShark copy() {
        return new HammerheadShark(this);
    }
}
