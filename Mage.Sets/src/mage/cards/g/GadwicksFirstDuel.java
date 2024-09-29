package mage.cards.g;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GadwicksFirstDuel extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("instant or sorcery spell with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GadwicksFirstDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create a Cursed Role token attached to up to one target creature.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new CreateRoleAttachedTargetEffect(RoleType.CURSED), new TargetCreaturePermanent(0, 1)
        );

        // II -- Scry 2.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new ScryEffect(2, false)
        );

        // III -- When you cast your next instant or sorcery spell with mana value 3 or less this turn, copy that spell. You may choose new targets for the copy.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility(filter))
        );
        this.addAbility(sagaAbility);
    }

    private GadwicksFirstDuel(final GadwicksFirstDuel card) {
        super(card);
    }

    @Override
    public GadwicksFirstDuel copy() {
        return new GadwicksFirstDuel(this);
    }
}
