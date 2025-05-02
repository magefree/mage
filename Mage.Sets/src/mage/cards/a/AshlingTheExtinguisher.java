package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class AshlingTheExtinguisher extends CardImpl {

    public AshlingTheExtinguisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        // Whenever Ashling, the Extinguisher deals combat damage to a player, choose target creature that player controls. they sacrifice that creature.
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        Effect effect = new SacrificeTargetEffect().setText("choose target creature that player controls. The player sacrifices that creature");
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private AshlingTheExtinguisher(final AshlingTheExtinguisher card) {
        super(card);
    }

    @Override
    public AshlingTheExtinguisher copy() {
        return new AshlingTheExtinguisher(this);
    }

}
