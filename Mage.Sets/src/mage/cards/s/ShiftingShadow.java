package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShiftingShadow extends CardImpl {

    public ShiftingShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature has haste and “At the beginning of your upkeep, destroy this creature. Reveal cards from the top of your library until you reveal a creature card.
        // Put that card onto the battlefield and attach Shifting Shadow to it, then put all other cards revealed this way on the bottom of your library in a random order.”
        this.addAbility(new SimpleStaticAbility(new ShiftingShadowGainEffect()));
    }

    private ShiftingShadow(final ShiftingShadow card) {
        super(card);
    }

    @Override
    public ShiftingShadow copy() {
        return new ShiftingShadow(this);
    }
}

class ShiftingShadowGainEffect extends ContinuousEffectImpl {

    ShiftingShadowGainEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "enchanted creature has haste and \"At the beginning of your upkeep, " +
                "destroy this creature. Reveal cards from the top of your library until you reveal a creature card. " +
                "Put that card onto the battlefield and attach {this} to it, then put all other cards " +
                "revealed this way on the bottom of your library in a random order.\"";
    }

    private ShiftingShadowGainEffect(final ShiftingShadowGainEffect effect) {
        super(effect);
    }

    @Override
    public ShiftingShadowGainEffect copy() {
        return new ShiftingShadowGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = source.getSourcePermanentIfItStillExists(game);
        if (aura == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(aura.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
        permanent.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ShiftingShadowEffect(permanent, game), TargetController.YOU, false
        ));
        return true;
    }
}

class ShiftingShadowEffect extends OneShotEffect {

    private final MageObjectReference mor;
    private final String name;

    ShiftingShadowEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        this.mor = new MageObjectReference(permanent, game);
        this.name = permanent.getName();
    }

    private ShiftingShadowEffect(final ShiftingShadowEffect effect) {
        super(effect);
        mor = effect.mor;
        this.name = effect.name;
    }

    @Override
    public ShiftingShadowEffect copy() {
        return new ShiftingShadowEffect(this);
    }

    private static Card getCard(Player player, Cards cards, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isCreature(game)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.destroy(source, game);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(player, cards, game);
        player.revealCards(source, cards, game);
        Permanent creature;
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            creature = game.getPermanent(card.getId());
        } else {
            creature = null;
        }
        if (creature != null && mor.zoneCounterIsCurrent(game)) {
            creature.addAttachment(mor.getSourceId(), source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(card, game, source, false);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "destroy this creature. Reveal cards from the top of your library until you reveal a creature card. " +
                "Put that card onto the battlefield and attach " + name + " to it, then put all other cards " +
                "revealed this way on the bottom of your library in a random order.";
    }
}
