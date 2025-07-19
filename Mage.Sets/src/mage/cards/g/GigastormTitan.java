package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastAnotherSpellThisTurnCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GigastormTitan extends CardImpl {

    public GigastormTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {3} less to cast if you've cast another spell this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, CastAnotherSpellThisTurnCondition.instance)
                .setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(CastAnotherSpellThisTurnCondition.instance.getHint()));
    }

    private GigastormTitan(final GigastormTitan card) {
        super(card);
    }

    @Override
    public GigastormTitan copy() {
        return new GigastormTitan(this);
    }
}
