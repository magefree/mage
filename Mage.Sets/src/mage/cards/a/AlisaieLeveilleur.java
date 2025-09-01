package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouCastExactOneSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlisaieLeveilleur extends CardImpl {

    public AlisaieLeveilleur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Partner with Alphinaud Leveilleur
        this.addAbility(new PartnerWithAbility("Alphinaud Leveilleur"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Dualcast -- The second spell you cast each turn costs {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 2),
                YouCastExactOneSpellThisTurnCondition.instance, "the second spell you cast each turn costs {2} less to cast"
        )).withFlavorWord("Dualcast"));
    }

    private AlisaieLeveilleur(final AlisaieLeveilleur card) {
        super(card);
    }

    @Override
    public AlisaieLeveilleur copy() {
        return new AlisaieLeveilleur(this);
    }
}
