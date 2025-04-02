package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouCastExactOneSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author androosss
 */
public final class HighspireBellRinger extends CardImpl {

    public HighspireBellRinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // The second spell you cast each turn costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 1),
                YouCastExactOneSpellThisTurnCondition.instance, "the second spell you cast each turn costs {1} less to cast"
        )));
    }

    private HighspireBellRinger(final HighspireBellRinger card) {
        super(card);
    }

    @Override
    public HighspireBellRinger copy() {
        return new HighspireBellRinger(this);
    }
}