package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ShapeshifterBlueToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBearsOfLittjara extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            SubType.SHAPESHIFTER, "any number of target Shapeshifter creatures you control"
    );

    public TheBearsOfLittjara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Create a 2/2 blue Shapeshifter creature token with changeling.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new ShapeshifterBlueToken())
        );

        // II — Any number of target Shapeshifter creatures you control have base power and toughness 4/4.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new SetPowerToughnessTargetEffect(4, 4, Duration.Custom),
                new TargetPermanent(0, Integer.MAX_VALUE, filter, false)
        );

        // III — Choose up to one target creature or planeswalker. Each creature with power 4 or greater you control deals damage equal to its power to that permanent.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new TheBearsOfLittjaraEffect(), new TargetCreatureOrPlaneswalker(0, 1)
        );

        this.addAbility(sagaAbility);
    }

    private TheBearsOfLittjara(final TheBearsOfLittjara card) {
        super(card);
    }

    @Override
    public TheBearsOfLittjara copy() {
        return new TheBearsOfLittjara(this);
    }
}

class TheBearsOfLittjaraEffect extends OneShotEffect {

    TheBearsOfLittjaraEffect() {
        super(Outcome.Benefit);
        staticText = "Choose up to one target creature or planeswalker. " +
                "Each creature with power 4 or greater you control deals damage equal to its power to that permanent.";
    }

    private TheBearsOfLittjaraEffect(final TheBearsOfLittjaraEffect effect) {
        super(effect);
    }

    @Override
    public TheBearsOfLittjaraEffect copy() {
        return new TheBearsOfLittjaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source.getSourceId(), game
        )) {
            if (creature == null) {
                continue;
            }
            int power = creature.getPower().getValue();
            if (power >= 4) {
                permanent.damage(power, creature.getId(), source, game);
            }
        }
        return true;
    }
}
