package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.FederationCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author muz
 */
public final class SarekFederationAmbassador extends CardImpl {

    public SarekFederationAmbassador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Federation -- At the beginning of combat on your turn, draw a card for every five creature types among non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each creature you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new SarekFederationAmbassadorEffect())
            .setAbilityWord(AbilityWord.FEDERATION).addHint(FederationCount.instance.getHint()));
    }

    private SarekFederationAmbassador(final SarekFederationAmbassador card) {
        super(card);
    }

    @Override
    public SarekFederationAmbassador copy() {
        return new SarekFederationAmbassador(this);
    }
}

class SarekFederationAmbassadorEffect extends OneShotEffect {

    SarekFederationAmbassadorEffect() {
        super(Outcome.DrawCard);
        staticText = "draw a card for every five creature types among non-Borg creatures you control. "
            + "If you draw three or more cards this way, put two +1/+1 counters on each creature you control";
    }

    private SarekFederationAmbassadorEffect(final SarekFederationAmbassadorEffect effect) {
        super(effect);
    }

    @Override
    public SarekFederationAmbassadorEffect copy() {
        return new SarekFederationAmbassadorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int cardsDrawn = controller.drawCards(FederationCount.instance.calculate(game, source, this) / 5, source, game);
        if (cardsDrawn >= 3) {
            new AddCountersAllEffect(CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURES).apply(game, source);
        }
        return true;
    }
}
