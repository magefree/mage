package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.IzoniInsectToken;

import java.util.Objects;
import java.util.UUID;

public final class AmzuSwarmsHunger extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.INSECT);

    public AmzuSwarmsHunger (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");

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
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(new MenaceAbility(), Duration.WhileOnBattlefield, filter, true)));

        // Whenever one or more cards leave your graveyard, you may create a 1/1 black and green Insect creature token, then put a number of +1/+1 counters on it equal to the greatest mana value among those cards. Do this only once each turn.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new AmzuSwarmsHungerEffect()).setOptional(true).setDoOnlyOnceEachTurn(true));
    }

    private AmzuSwarmsHunger(final AmzuSwarmsHunger card) {
        super(card);
    }

    @Override
    public AmzuSwarmsHunger copy() {
        return new AmzuSwarmsHunger(this);
    }
}

class AmzuSwarmsHungerEffect extends CreateTokenEffect {
    AmzuSwarmsHungerEffect() {
        super(new IzoniInsectToken());
        this.staticText = "you may create a 1/1 black and green Insect creature token, " +
            "then put a number of +1/+1 counters on it equal to the greatest mana value among those cards. " +
            "Do this only once each turn.";
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
        final int greatest = ((ZoneChangeGroupEvent)(((TriggeredAbility)source).getTriggerEvent())).getCards().stream().mapToInt(Card::getManaValue).max().orElse(0);
        return super.apply(game, source) &&
            this.getLastAddedTokenIds().stream().map(game::getPermanent).filter(Objects::nonNull).allMatch(token -> token.addCounters(CounterType.P1P1.createInstance(greatest), source.getControllerId(), source, game));
    }
}
