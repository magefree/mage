package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetEquipmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenRebuke extends CardImpl {

    public MoltenRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Choose one or both--
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Molten Rebuke deals 5 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // * Destroy target Equipment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEquipmentPermanent()));
    }

    private MoltenRebuke(final MoltenRebuke card) {
        super(card);
    }

    @Override
    public MoltenRebuke copy() {
        return new MoltenRebuke(this);
    }
}
