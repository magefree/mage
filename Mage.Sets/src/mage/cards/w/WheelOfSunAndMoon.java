
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class WheelOfSunAndMoon extends CardImpl {

    public WheelOfSunAndMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G/W}{G/W}");
        this.subtype.add(SubType.AURA);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // If a card would be put into enchanted player's graveyard from anywhere, instead that card is revealed and put on the bottom of that player's library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WheelOfSunAndMoonEffect()));
    }

    private WheelOfSunAndMoon(final WheelOfSunAndMoon card) {
        super(card);
    }

    @Override
    public WheelOfSunAndMoon copy() {
        return new WheelOfSunAndMoon(this);
    }
}

class WheelOfSunAndMoonEffect extends ReplacementEffectImpl {

    public WheelOfSunAndMoonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If a card would be put into enchanted player's graveyard from anywhere, instead that card is revealed and put on the bottom of that player's library";
    }

    public WheelOfSunAndMoonEffect(final WheelOfSunAndMoonEffect effect) {
        super(effect);
    }

    @Override
    public WheelOfSunAndMoonEffect copy() {
        return new WheelOfSunAndMoonEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Permanent enchantment = game.getPermanent(source.getSourceId());
                if (enchantment != null && enchantment.getAttachedTo() != null
                        && card.isOwnedBy(enchantment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                return true;
            }
        }
        return false;
    }

}
