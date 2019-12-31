
package mage.cards.d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.choices.ManaChoice;
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
 * @author Plopman
 */
public final class DawnsReflection extends CardImpl {

    public DawnsReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors.
        this.addAbility(new DawnsReflectionTriggeredAbility());
    }

    public DawnsReflection(final DawnsReflection card) {
        super(card);
    }

    @Override
    public DawnsReflection copy() {
        return new DawnsReflection(this);
    }
}

class DawnsReflectionTriggeredAbility extends TriggeredManaAbility {

    public DawnsReflectionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DawnsReflectionManaEffect());
    }

    public DawnsReflectionTriggeredAbility(final DawnsReflectionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DawnsReflectionTriggeredAbility copy() {
        return new DawnsReflectionTriggeredAbility(this);
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

class DawnsReflectionManaEffect extends ManaEffect {

    public DawnsReflectionManaEffect() {
        super();
        this.staticText = "its controller adds two mana in any combination of colors";
    }

    public DawnsReflectionManaEffect(final DawnsReflectionManaEffect effect) {
        super(effect);
    }

    @Override
    public DawnsReflectionManaEffect copy() {
        return new DawnsReflectionManaEffect(this);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanentAttachedTo = game.getPermanent(enchantment.getAttachedTo());
            if (permanentAttachedTo != null) {
                return game.getPlayer(permanentAttachedTo.getControllerId());
            }
        }
        return null;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Player player = getPlayer(game, source);
        return ManaChoice.chooseAnyColor(player, game, 2);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(new Mana(0, 0, 0, 0, 0, 0, 2, 0));
        return netMana;
    }

}
