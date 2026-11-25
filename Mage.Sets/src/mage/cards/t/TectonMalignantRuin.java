package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class TectonMalignantRuin extends CardImpl {

    private static final DynamicValue xValue
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT);

    public TectonMalignantRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Fathomless descent -- This spell costs {1} less to cast for each permanent card in your graveyard.
        Ability ability = new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        );
        ability.setAbilityWord(AbilityWord.FATHOMLESS_DESCENT);
        ability.setRuleAtTheTop(true);
        ability.addHint(DescendCondition.getHint());
        this.addAbility(ability);

        // Menace
        this.addAbility(new MenaceAbility());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you sacrifice another permanent, draw a card.
        this.addAbility(new SacrificePermanentTriggeredAbility(
            new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_ANOTHER_PERMANENT
        ));
    }

    private TectonMalignantRuin(final TectonMalignantRuin card) {
        super(card);
    }

    @Override
    public TectonMalignantRuin copy() {
        return new TectonMalignantRuin(this);
    }
}
