package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OreScaleGuardian extends CardImpl {

    public OreScaleGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each land card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Land card in your graveyard", xValue));
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

    }

    private OreScaleGuardian(final OreScaleGuardian card) {
        super(card);
    }

    @Override
    public OreScaleGuardian copy() {
        return new OreScaleGuardian(this);
    }
}
