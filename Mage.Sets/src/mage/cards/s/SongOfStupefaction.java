package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongOfStupefaction extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT, -1);

    public SongOfStupefaction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or Vehicle
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Song of Stupefaction enters the battlefield, you may mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2), true));

        // Fathomless descent -- Enchanted permanent gets -X/-0, where X is the number of permanent cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(xValue, StaticValue.get(0))
                .setText("enchanted permanent gets -X/-0, where X is the number of permanent cards in your graveyard"))
                .setAbilityWord(AbilityWord.FATHOMLESS_DESCENT).addHint(DescendCondition.getHint()));
    }

    private SongOfStupefaction(final SongOfStupefaction card) {
        super(card);
    }

    @Override
    public SongOfStupefaction copy() {
        return new SongOfStupefaction(this);
    }
}
