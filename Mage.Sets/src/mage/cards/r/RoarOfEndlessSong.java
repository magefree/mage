package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elephant55Token;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoarOfEndlessSong extends CardImpl {

    public RoarOfEndlessSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Create a 5/5 green Elephant creature token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new Elephant55Token()));

        // III -- Double the power and toughness of each creature you control until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new RoarOfEndlessSongEffect());
        this.addAbility(sagaAbility);
    }

    private RoarOfEndlessSong(final RoarOfEndlessSong card) {
        super(card);
    }

    @Override
    public RoarOfEndlessSong copy() {
        return new RoarOfEndlessSong(this);
    }
}

class RoarOfEndlessSongEffect extends OneShotEffect {

    RoarOfEndlessSongEffect() {
        super(Outcome.Benefit);
        staticText = "double the power and toughness of each creature you control until end of turn";
    }

    private RoarOfEndlessSongEffect(final RoarOfEndlessSongEffect effect) {
        super(effect);
    }

    @Override
    public RoarOfEndlessSongEffect copy() {
        return new RoarOfEndlessSongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            game.addEffect(new BoostTargetEffect(
                    permanent.getPower().getValue(),
                    permanent.getToughness().getValue()
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
