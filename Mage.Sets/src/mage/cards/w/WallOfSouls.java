package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtCombatDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WallOfSouls extends CardImpl {

    public WallOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Souls is dealt combat damage, it deals that much damage to target opponent or planeswalker.
        Ability ability = new DealtCombatDamageToSourceTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH), false);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private WallOfSouls(final WallOfSouls card) {
        super(card);
    }

    @Override
    public WallOfSouls copy() {
        return new WallOfSouls(this);
    }
}
