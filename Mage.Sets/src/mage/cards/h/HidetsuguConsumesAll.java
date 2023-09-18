package mage.cards.h;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HidetsuguConsumesAll extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public HidetsuguConsumesAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.v.VesselOfTheAllConsuming.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Destroy each nonland permanent with mana value 1 or less.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DestroyAllEffect(filter)
                .setText("destroy each nonland permanent with mana value 1 or less"));

        // II — Exile all graveyards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ExileGraveyardAllPlayersEffect());

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility, mage.cards.v.VesselOfTheAllConsuming.makeWatcher());
    }

    private HidetsuguConsumesAll(final HidetsuguConsumesAll card) {
        super(card);
    }

    @Override
    public HidetsuguConsumesAll copy() {
        return new HidetsuguConsumesAll(this);
    }
}
