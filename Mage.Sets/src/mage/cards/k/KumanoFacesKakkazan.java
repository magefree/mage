package mage.cards.k;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.AddCounterNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class KumanoFacesKakkazan extends CardImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KumanoFacesKakkazan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.e.EtchingOfKumano.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Kumano Faces Kakkazan deals 1 damage to each opponent and each planeswalker they control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                new DamageAllEffect(1, filter).setText("and each planeswalker they control")
        );

        // II — When you cast your next creature spell this turn, that creature enters the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateDelayedTriggeredAbilityEffect(new AddCounterNextSpellDelayedTriggeredAbility())
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private KumanoFacesKakkazan(final KumanoFacesKakkazan card) {
        super(card);
    }

    @Override
    public KumanoFacesKakkazan copy() {
        return new KumanoFacesKakkazan(this);
    }
}
