package mage.cards.m;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinecartDaredevil extends AdventureCard {

    public MinecartDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{R}", "Ride the Rails", "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Ride the Rails
        // Target creature gets +2/+1 until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private MinecartDaredevil(final MinecartDaredevil card) {
        super(card);
    }

    @Override
    public MinecartDaredevil copy() {
        return new MinecartDaredevil(this);
    }
}
