package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllNonCombatDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystalBarricade extends CardImpl {

    public CrystalBarricade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // Prevent all noncombat damage that would be dealt to other creatures you control.
        this.addAbility(new SimpleStaticAbility(new PreventAllNonCombatDamageToAllEffect(
                Duration.WhileOnBattlefield, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES
        )));
    }

    private CrystalBarricade(final CrystalBarricade card) {
        super(card);
    }

    @Override
    public CrystalBarricade copy() {
        return new CrystalBarricade(this);
    }
}
