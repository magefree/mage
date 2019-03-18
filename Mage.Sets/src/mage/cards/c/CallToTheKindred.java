
package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public final class CallToTheKindred extends CardImpl {

    public CallToTheKindred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        // At the beginning of your upkeep, you may look at the top five cards of your library.
        // If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield,
        // then you put the rest of those cards on the bottom of your library in any order.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CallToTheKindredEffect(), true));
    }

    public CallToTheKindred(final CallToTheKindred card) {
        super(card);
    }

    @Override
    public CallToTheKindred copy() {
        return new CallToTheKindred(this);
    }
}

class CallToTheKindredEffect extends OneShotEffect {

    public CallToTheKindredEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "look at the top five cards of your library. If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield, then you put the rest of those cards on the bottom of your library in any order";
    }

    public CallToTheKindredEffect(final CallToTheKindredEffect effect) {
        super(effect);
    }

    @Override
    public CallToTheKindredEffect copy() {
        return new CallToTheKindredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());

        if (enchantment == null || controller == null || enchantment.getAttachedTo() == null) {
            return false;
        }

        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (creature == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        controller.lookAtCards(enchantment.getIdName(), cards, game);

        FilterCreatureCard filter = new FilterCreatureCard();

        if (!creature.getAbilities().contains(ChangelingAbility.getInstance())) {
            StringBuilder sb = new StringBuilder("creature card with at least one subtype from: ");
            List<Predicate<MageObject>> subtypes = new ArrayList<>();
            for (SubType subtype : creature.getSubtype(game)) {
                subtypes.add(new SubtypePredicate(subtype));
                sb.append(subtype).append(", ");
            }
            filter.add(Predicates.or(subtypes));
            sb.delete(sb.length() - 2, sb.length());
            filter.setMessage(sb.toString());
        } else {
            filter.setMessage("creature card that shares a creature type with enchanted creature");
        }

        if (cards.count(filter, game) > 0 && controller.chooseUse(Outcome.DrawCard, "Do you wish to put a creature card onto the battlefield?", source, game)) {
            TargetCard target = new TargetCard(Zone.LIBRARY, filter);

            if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
