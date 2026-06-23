package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;
import mage.game.permanent.token.VillainToken;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class AvengersUnderSiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Villain creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.VILLAIN);

    static {
        filter.add(Predicates.not(SubType.VILLAIN.getPredicate()));
    }

    public AvengersUnderSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create two 2/1 black Villain creature tokens with menace.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
            new CreateTokenEffect(new VillainToken(), 2)
        );

        // II -- This Saga deals 2 damage to each non-Villain creature and each opponent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
            new DamageAllEffect(2, filter),
            new DamagePlayersEffect(2, TargetController.OPPONENT).setText("and each opponent")
        );

        // III -- Create a Treasure token for each Villain you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
            new CreateTokenEffect(new TreasureToken(), new PermanentsOnBattlefieldCount(filter2))
                .setText("create a Treasure token for each Villain you control")
        );

        this.addAbility(sagaAbility);
    }

    private AvengersUnderSiege(final AvengersUnderSiege card) {
        super(card);
    }

    @Override
    public AvengersUnderSiege copy() {
        return new AvengersUnderSiege(this);
    }
}
