package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class TomorrowAzamisFamiliar extends CardImpl {

    public TomorrowAzamisFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // If you would draw a card, look at the top three cards of your library instead. Put one of those cards into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new SimpleStaticAbility(new TomorrowAzamisFamiliarReplacementEffect()));
    }

    private TomorrowAzamisFamiliar(final TomorrowAzamisFamiliar card) {
        super(card);
    }

    @Override
    public TomorrowAzamisFamiliar copy() {
        return new TomorrowAzamisFamiliar(this);
    }
}

class TomorrowAzamisFamiliarReplacementEffect extends ReplacementEffectImpl {

    TomorrowAzamisFamiliarReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, look at the top three cards of your library instead. Put one of those cards into your hand and the rest on the bottom of your library in any order";
    }

    TomorrowAzamisFamiliarReplacementEffect(final TomorrowAzamisFamiliarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TomorrowAzamisFamiliarReplacementEffect copy() {
        return new TomorrowAzamisFamiliarReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY).apply(game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }   
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
