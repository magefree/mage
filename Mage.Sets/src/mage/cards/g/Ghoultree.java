package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class Ghoultree extends CardImpl {

    public Ghoultree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Ghoultree costs {1} less to cast for each creature card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Creature card in your graveyard", xValue));
        this.addAbility(ability);
    }

    private Ghoultree(final Ghoultree card) {
        super(card);
    }

    @Override
    public Ghoultree copy() {
        return new Ghoultree(this);
    }
}
