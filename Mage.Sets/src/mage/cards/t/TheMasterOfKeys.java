package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Callumvl
 */
public final class TheMasterOfKeys extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public TheMasterOfKeys(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{X}{W}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When The Master of Keys enters, put X +1/+1 counters on it and mill twice X cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), GetXValue.instance).setText("put X +1/+1 counters on it"));
        ability.addEffect(new MillCardsControllerEffect(xValue).setText("and mill twice X cards."));
        this.addAbility(ability);

        // Each enchantment card in your graveyard has escape. The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(new TheMasterOfKeysEffect()));
    }

    private TheMasterOfKeys(final TheMasterOfKeys card) {
        super(card);
    }

    @Override
    public TheMasterOfKeys copy() {
        return new TheMasterOfKeys(this);
    }
}

class TheMasterOfKeysEffect extends ContinuousEffectImpl {

    TheMasterOfKeysEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each enchantment card in your graveyard has escape. " +
                "The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.";
    }

    private TheMasterOfKeysEffect(final TheMasterOfKeysEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> !card.getManaCost().getText().isEmpty()) // card must have a mana cost
                .filter(card -> card.isEnchantment(game)) // must be enchantment
                .forEach(card -> {
                    Ability ability = new EscapeAbility(card, card.getManaCost().getText(), 3);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }

    @Override
    public TheMasterOfKeysEffect copy() {
        return new TheMasterOfKeysEffect(this);
    }
}
