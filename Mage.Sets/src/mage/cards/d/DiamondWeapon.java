package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiamondWeapon extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT);

    public DiamondWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {1} less to cast for each permanent card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)).addHint(DescendCondition.getHint()));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Immune -- Prevent all combat damage that would be dealt to Diamond Weapon.
        this.addAbility(new SimpleStaticAbility(new PreventCombatDamageToSourceEffect(Duration.WhileOnBattlefield)).withFlavorWord("Immune"));
    }

    private DiamondWeapon(final DiamondWeapon card) {
        super(card);
    }

    @Override
    public DiamondWeapon copy() {
        return new DiamondWeapon(this);
    }
}
