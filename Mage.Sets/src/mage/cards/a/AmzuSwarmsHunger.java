package mage.cards.a;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IzoniInsectToken;

/**
 *
 * @author jimga150
 */
public final class AmzuSwarmsHunger extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.INSECT, "Insects");

    public AmzuSwarmsHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Other Insects you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield,
                filter, true
        )));

        // Whenever one or more cards leave your graveyard, you may create a 1/1 black and green Insect creature token,
        // then put a number of +1/+1 counters on it equal to the greatest mana value among those cards.
        // Do this only once each turn.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new AmzuSwarmsHungerEffect()).setDoOnlyOnceEachTurn(true));
    }

    private AmzuSwarmsHunger(final AmzuSwarmsHunger card) {
        super(card);
    }

    @Override
    public AmzuSwarmsHunger copy() {
        return new AmzuSwarmsHunger(this);
    }
}

// Based on OutlawStitcherEffect
class AmzuSwarmsHungerEffect extends OneShotEffect {

    AmzuSwarmsHungerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 black and green Insect creature token, " +
                "then put a number of +1/+1 counters on it equal to the greatest mana value among those cards.";
    }

    private AmzuSwarmsHungerEffect(final AmzuSwarmsHungerEffect effect) {
        super(effect);
    }

    @Override
    public AmzuSwarmsHungerEffect copy() {
        return new AmzuSwarmsHungerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new IzoniInsectToken());
        boolean result = effect.apply(game, source);
        if (!result){
            return false;
        }

        Object cardsLeavingGraveyardObj = this.getValue("cardsLeavingGraveyard");
        if (!(cardsLeavingGraveyardObj instanceof Set)) {
            return false;
        }
        Set<Card> cardsLeavingGraveyard = (Set<Card>) cardsLeavingGraveyardObj;
        int xvalue = cardsLeavingGraveyard
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);

        if (xvalue <= 0) {
            return true;
        }
        for (UUID id : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(id);
            if (token == null) {
                continue;
            }
            token.addCounters(CounterType.P1P1.createInstance(xvalue), source.getControllerId(), source, game);
        }
        return true;
    }

}
