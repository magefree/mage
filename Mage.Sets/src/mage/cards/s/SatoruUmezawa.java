package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SatoruUmezawa extends CardImpl {

    public SatoruUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you activate a ninjutsu ability, look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order. This ability triggers only once each turn.
        this.addAbility(new SatoruUmezawaTriggeredAbility());

        // Each creature card in your hand has ninjutsu {2}{U}{B}.
        this.addAbility(new SimpleStaticAbility(new SatoruUmezawaEffect()));
    }

    private SatoruUmezawa(final SatoruUmezawa card) {
        super(card);
    }

    @Override
    public SatoruUmezawa copy() {
        return new SatoruUmezawa(this);
    }
}

class SatoruUmezawaTriggeredAbility extends TriggeredAbilityImpl {

    SatoruUmezawaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false, false
        ));
        this.setTriggersOnce(true);
    }

    private SatoruUmezawaTriggeredAbility(final SatoruUmezawaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SatoruUmezawaTriggeredAbility copy() {
        return new SatoruUmezawaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
        return stackAbility.getStackAbility() instanceof NinjutsuAbility;
    }

    @Override
    public String getRule() {
        return "Whenever you activate a ninjutsu ability, look at the top three cards of your library. " +
                "Put one of them into your hand and the rest on the bottom of your library in any order. " +
                "This ability triggers only once each turn.";
    }
}

class SatoruUmezawaEffect extends ContinuousEffectImpl {

    public SatoruUmezawaEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "each creature card in your hand has ninjutsu {2}{U}{B}";
    }

    public SatoruUmezawaEffect(final SatoruUmezawaEffect effect) {
        super(effect);
    }

    @Override
    public SatoruUmezawaEffect copy() {
        return new SatoruUmezawaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getHand().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            game.getState().addOtherAbility(card, new NinjutsuAbility(new ManaCostsImpl<>("{2}{U}{B}")));
        }
        return true;
    }
}
