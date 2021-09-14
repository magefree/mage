package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OboshThePreypiercer extends CardImpl {

    public OboshThePreypiercer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B/R}{B/R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HELLION);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Companion — Your starting deck contains only cards with odd converted mana costs and land cards.
        this.addAbility(new CompanionAbility(OboshThePreypiercerCompanionCondition.instance));

        // If a source you control with an odd converted mana cost would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new OboshThePreypiercerEffect()));
    }

    private OboshThePreypiercer(final OboshThePreypiercer card) {
        super(card);
    }

    @Override
    public OboshThePreypiercer copy() {
        return new OboshThePreypiercer(this);
    }
}

enum OboshThePreypiercerCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Your starting deck contains only cards with odd mana values and land cards.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
        return deck
                .stream()
                .filter(card -> !card.hasCardTypeForDeckbuilding(CardType.LAND))
                .mapToInt(MageObject::getManaValue)
                .map(i -> i % 2)
                .allMatch(i -> i == 1);
    }
}

class OboshThePreypiercerEffect extends ReplacementEffectImpl {

    OboshThePreypiercerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source you control with an odd mana value would deal damage " +
                "to a permanent or player, it deals double that damage to that permanent or player instead.";
    }

    private OboshThePreypiercerEffect(final OboshThePreypiercerEffect effect) {
        super(effect);
    }

    @Override
    public OboshThePreypiercerEffect copy() {
        return new OboshThePreypiercerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_PERMANENT);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject sourceObject = game.getObject(event.getSourceId());
        return sourceObject != null
                && sourceObject.getManaValue() % 2 == 1
                && game.getControllerId(event.getSourceId()).equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
