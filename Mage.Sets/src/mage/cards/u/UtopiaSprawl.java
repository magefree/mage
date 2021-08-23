package mage.cards.u;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
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

import java.util.UUID;

/**
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
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new UtopiaSprawlEffect(),"Forest"));
    }

    private UtopiaSprawl(final UtopiaSprawl card) {
        super(card);
    }

    @Override
    public UtopiaSprawl copy() {
        return new UtopiaSprawl(this);
    }
}
class UtopiaSprawlEffect extends ManaEffect {

     UtopiaSprawlEffect() {
        super();
        staticText = "its controller adds an additional one mana of the chosen color";
    }

    private UtopiaSprawlEffect(final UtopiaSprawlEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                return game.getPlayer(land.getControllerId());
            }
        }
        return null;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color != null) {
                return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
            }
        }
        return new Mana();
    }

    @Override
    public UtopiaSprawlEffect copy() {
        return new UtopiaSprawlEffect(this);
    }
}
