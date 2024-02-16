package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterHistoricCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoGrant extends CardImpl {

    public JoGrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Each historic card in your hand has cycling {2}{W}.
        this.addAbility(new SimpleStaticAbility(new JoGrantEffect()));

        // Whenever you cycle a card, put a +1/+1 counter on Jo Grant.
        this.addAbility(new CycleControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private JoGrant(final JoGrant card) {
        super(card);
    }

    @Override
    public JoGrant copy() {
        return new JoGrant(this);
    }
}

class JoGrantEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterHistoricCard();

    JoGrantEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "each historic card in your hand has cycling {2}{W}";
    }

    private JoGrantEffect(final JoGrantEffect effect) {
        super(effect);
    }

    @Override
    public JoGrantEffect copy() {
        return new JoGrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(filter, game)) {
            game.getState().addOtherAbility(card, new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));
        }
        return true;
    }
}
