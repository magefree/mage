package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.ReconfigureAbility;
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
public final class TheRealityChip extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public TheRealityChip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.JELLYFISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // As long as The Reality Chip is attached to a creature, you may play lands and cast spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(new PlayTheTopCardEffect(), condition)
                .setText("as long as {this} is attached to a creature, you may play lands and cast spells from the top of your library")));

        // Reconfigure {2}{U}
        this.addAbility(new ReconfigureAbility("{2}{U}"));
    }

    private TheRealityChip(final TheRealityChip card) {
        super(card);
    }

    @Override
    public TheRealityChip copy() {
        return new TheRealityChip(this);
    }
}
