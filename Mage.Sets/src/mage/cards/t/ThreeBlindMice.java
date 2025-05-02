package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.MouseToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreeBlindMice extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public ThreeBlindMice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Create a 1/1 white Mouse creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new MouseToken()));

        // II, III -- Create a token that's a copy of target token you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new CreateTokenCopyTargetEffect(), new TargetPermanent(filter)
        );

        // IV -- Creatures you control get +1/+1 and gain vigilance until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1"),
                new GainAbilityControlledEffect(
                        VigilanceAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("and gain vigilance until end of turn")
        );
        this.addAbility(sagaAbility);
    }

    private ThreeBlindMice(final ThreeBlindMice card) {
        super(card);
    }

    @Override
    public ThreeBlindMice copy() {
        return new ThreeBlindMice(this);
    }
}
