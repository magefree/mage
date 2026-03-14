package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.EdgarMarkovsCoffinVampireToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgarCharmedGroom extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VAMPIRE, "Vampires");
    private static final Condition condition = new SourceHasCounterCondition(CounterType.BLOODLINE, 3);

    public EdgarCharmedGroom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.NOBLE}, "{2}{W}{B}",
                "Edgar Markov's Coffin",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "WB"
        );

        // Edgar, Charmed Groom
        this.getLeftHalfCard().setPT(4, 4);

        // Other Vampires you control get +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // When Edgar, Charmed Groom dies, return it to the battlefield transformed under its owner's control.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new EdgarCharmedGroomEffect()));

        // Edgar Markov's Coffin

        // At the beginning of your upkeep, create a 1/1 white and black Vampire creature token with lifelink and put a bloodline counter on Edgar Markov's Coffin. Then if there are three or more bloodline counters on it, remove those counters and transform it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new EdgarMarkovsCoffinVampireToken()));
        ability.addEffect(new AddCountersSourceEffect(CounterType.BLOODLINE.createInstance()).concatBy("and"));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.BLOODLINE), condition,
                "Then if there are three or more bloodline counters on it, remove those counters and transform it"
        ).addEffect(new TransformSourceEffect()));
        this.getRightHalfCard().addAbility(ability);
    }

    private EdgarCharmedGroom(final EdgarCharmedGroom card) {
        super(card);
    }

    @Override
    public EdgarCharmedGroom copy() {
        return new EdgarCharmedGroom(this);
    }
}

class EdgarCharmedGroomEffect extends OneShotEffect {

    EdgarCharmedGroomEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield transformed under its owner's control";
    }

    private EdgarCharmedGroomEffect(final EdgarCharmedGroomEffect effect) {
        super(effect);
    }

    @Override
    public EdgarCharmedGroomEffect copy() {
        return new EdgarCharmedGroomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }
}
