
package mage.cards.u;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
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
public final class UtopiaSprawl extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.FOREST, "Forest");

    public UtopiaSprawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant Forest
        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // As Utopia Sprawl enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));
        // Whenever enchanted Forest is tapped for mana, its controller adds one mana of the chosen color.
        this.addAbility(new UtopiaSprawlTriggeredAbility());
    }

    public UtopiaSprawl(final UtopiaSprawl card) {
        super(card);
    }

    @Override
    public UtopiaSprawl copy() {
        return new UtopiaSprawl(this);
    }
}

class UtopiaSprawlTriggeredAbility extends TriggeredManaAbility {

    public UtopiaSprawlTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UtopiaSprawlEffect());
    }

    public UtopiaSprawlTriggeredAbility(UtopiaSprawlTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public UtopiaSprawlTriggeredAbility copy() {
        return new UtopiaSprawlTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted Forest is tapped for mana, its controller adds one mana of the chosen color.";
    }
}

class UtopiaSprawlEffect extends ManaEffect {

    public UtopiaSprawlEffect() {
        super();
        staticText = "its controller adds one mana of the chosen color";
    }

    public UtopiaSprawlEffect(final UtopiaSprawlEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                Player player = game.getPlayer(land.getControllerId());
                if (player != null) {
                    player.getManaPool().addMana(getMana(game, source), game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
        } else {
            return null;
        }
    }

    @Override
    public UtopiaSprawlEffect copy() {
        return new UtopiaSprawlEffect(this);
    }
}
