package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.RatRogueToken;

/**
 *
 * @author weirddan455
 */
public final class TributeToHorobi extends CardImpl {

    public TributeToHorobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.e.EchoOfDeathsWail.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Each opponent creates a 1/1 black Rat Rouge creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new TributeToHorobiTokenEffect());

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private TributeToHorobi(final TributeToHorobi card) {
        super(card);
    }

    @Override
    public TributeToHorobi copy() {
        return new TributeToHorobi(this);
    }
}

class TributeToHorobiTokenEffect extends OneShotEffect {

    public TributeToHorobiTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each opponent creates a 1/1 black Rat Rouge creature token";
    }

    private TributeToHorobiTokenEffect(final TributeToHorobiTokenEffect effect) {
        super(effect);
    }

    @Override
    public TributeToHorobiTokenEffect copy() {
        return new TributeToHorobiTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean success = false;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (new RatRogueToken().putOntoBattlefield(1, game, source, opponentId)) {
                success = true;
            }
        }
        return success;
    }
}
