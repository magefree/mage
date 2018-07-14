
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class MarketFestival extends CardImpl {

    public MarketFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors (in addition to the mana the land produces).
        this.addAbility(new MarketFestivalTriggeredAbility());
    }

    public MarketFestival(final MarketFestival card) {
        super(card);
    }

    @Override
    public MarketFestival copy() {
        return new MarketFestival(this);
    }
}

class MarketFestivalTriggeredAbility extends TriggeredManaAbility {

    public MarketFestivalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MarketFestivalManaEffect());
    }

    public MarketFestivalTriggeredAbility(final MarketFestivalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarketFestivalTriggeredAbility copy() {
        return new MarketFestivalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && enchantment.isAttachedTo(event.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors <i>(in addition to the mana the land produces)</i>.";
    }
}

class MarketFestivalManaEffect extends ManaEffect {

    public MarketFestivalManaEffect() {
        super();
        this.staticText = "its controller adds two mana in any combination of colors";
    }

    public MarketFestivalManaEffect(final MarketFestivalManaEffect effect) {
        super(effect);
    }

    @Override
    public MarketFestivalManaEffect copy() {
        return new MarketFestivalManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            int x = 2;

            Mana mana = new Mana();
            for (int i = 0; i < x; i++) {
                ChoiceColor choiceColor = new ChoiceColor();
                if (i == 0) {
                    choiceColor.setMessage("First mana color for " + sourceObject.getLogName());
                } else {
                    choiceColor.setMessage("Second mana color for " + sourceObject.getLogName());
                }
                if (!controller.choose(Outcome.Benefit, choiceColor, game)) {
                    return null;
                }
                if (choiceColor.getChoice() == null) { // Possible after reconnect?
                    return null;
                }
                choiceColor.increaseMana(mana);
            }
            return mana;
        }
        return null;
    }

}
