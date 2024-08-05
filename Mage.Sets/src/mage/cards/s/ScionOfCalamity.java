package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ScionOfCalamity extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment that player controls");

    public ScionOfCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Myriad
        this.addAbility(new MyriadAbility(true));

        // Whenever Scion of Calamity deals combat damage to a player, destroy target artifact or enchantment that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private ScionOfCalamity(final ScionOfCalamity card) {
        super(card);
    }

    @Override
    public ScionOfCalamity copy() {
        return new ScionOfCalamity(this);
    }
}

