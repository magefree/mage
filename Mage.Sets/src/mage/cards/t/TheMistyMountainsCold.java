package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Dragon66Token;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author muz
 */
public final class TheMistyMountainsCold extends CardImpl {

    public TheMistyMountainsCold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III, IV -- Create a Treasure token. Then if you control four or more Treasures, sacrifice this Saga. If you do, create a 6/6 red Dragon creature token with flying.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_IV,
            new TheMistyMountainsColdEffect()
        );

        this.addAbility(sagaAbility);
    }

    private TheMistyMountainsCold(final TheMistyMountainsCold card) {
        super(card);
    }

    @Override
    public TheMistyMountainsCold copy() {
        return new TheMistyMountainsCold(this);
    }
}

class TheMistyMountainsColdEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "Treasures");

    TheMistyMountainsColdEffect() {
        super(Outcome.Benefit);
        staticText = "create a Treasure token. Then if you control four or more Treasures, sacrifice {this}. If you do, create a 6/6 red Dragon creature token with flying";
    }

    private TheMistyMountainsColdEffect(final TheMistyMountainsColdEffect effect) {
        super(effect);
    }

    @Override
    public TheMistyMountainsColdEffect copy() {
        return new TheMistyMountainsColdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new CreateTokenEffect(new TreasureToken()).apply(game, source);
        if (game.getBattlefield().count(filter, source.getControllerId(), source, game) < 4) {
            return true;
        }

        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.sacrifice(source, game)) {
            new CreateTokenEffect(new Dragon66Token()).apply(game, source);
        }
        return true;
    }
}
