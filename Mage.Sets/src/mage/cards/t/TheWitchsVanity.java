package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWitchsVanity extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("creature an opponent controls with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public TheWitchsVanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Destroy target creature an opponent controls with mana value 2 or less.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DestroyTargetEffect(),
                new TargetPermanent(filter)
        );

        // II -- Create a Food token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new FoodToken()));

        // III -- Create a Wicked Role token attached to target creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateRoleAttachedTargetEffect(RoleType.WICKED),
                new TargetControlledCreaturePermanent()
        );

        this.addAbility(sagaAbility);
    }

    private TheWitchsVanity(final TheWitchsVanity card) {
        super(card);
    }

    @Override
    public TheWitchsVanity copy() {
        return new TheWitchsVanity(this);
    }
}
