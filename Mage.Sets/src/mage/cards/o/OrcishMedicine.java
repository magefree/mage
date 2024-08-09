package mage.cards.o;

import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrcishMedicine extends CardImpl {

    public OrcishMedicine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gains your choice of lifelink or indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainsChoiceOfAbilitiesEffect(
                LifelinkAbility.getInstance(), IndestructibleAbility.getInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Amass Orcs 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ORC).concatBy("<br>"));
    }

    private OrcishMedicine(final OrcishMedicine card) {
        super(card);
    }

    @Override
    public OrcishMedicine copy() {
        return new OrcishMedicine(this);
    }
}
