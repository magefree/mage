package mage.cards.n;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.PutCardFromOneOfTwoZonesOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnToBattlefieldAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Alex-Vasile
 */
public class NextOfKin extends CardImpl {

    public NextOfKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.addSubType(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When enchanted creature dies, you may put a creature card you own with lesser mana value from your hand or from the command zone onto the battlefield.
        // If you do, return Next of Kin to the battlefield attached to that creature at the beginning of the next end step.
        this.addAbility(new DiesAttachedTriggeredAbility(new NextOfKinDiesEffect(), "enchanted creature", true));
    }

    private NextOfKin(final NextOfKin card) {
        super(card);
    }

    @Override
    public NextOfKin copy() {
        return new NextOfKin(this);
    }
}

class NextOfKinDiesEffect extends OneShotEffect {

    NextOfKinDiesEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may put a creature card you own with lesser mana value from your hand or from the command zone onto the battlefield. " +
                "If you do, return {this} to the battlefield attached to that creature at the beginning of the next end step.";
    }

    private NextOfKinDiesEffect(final NextOfKinDiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card nextOfKinCard = (Card) source.getSourceObjectIfItStillExists(game);
        Object object = getValue("attachedTo");
        if (controller == null || nextOfKinCard == null || !(object instanceof Permanent)) {
            return false;
        }
        int manaValue = ((Permanent) object).getManaValue();

        FilterCreatureCard filterCreatureCard = new FilterCreatureCard("a creature card you own with lesser mana value from your hand or from the command zone");
        filterCreatureCard.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, manaValue));

        // This effect is used only to get the info about which card was added.
        Effect hackTargetEffect = new InfoEffect("");

        Effect putCardEffect = new PutCardFromOneOfTwoZonesOntoBattlefieldEffect(filterCreatureCard, false, hackTargetEffect, Zone.HAND, Zone.COMMAND);
        boolean cardPut = putCardEffect.apply(game, source);
        if (!cardPut) {
            return false;
        }

        Effect returnToBattlefieldAttachedEffect = new ReturnToBattlefieldAttachedEffect();
        returnToBattlefieldAttachedEffect.setTargetPointer(hackTargetEffect.getTargetPointer());
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToBattlefieldAttachedEffect), source);
        return true;
    }

    @Override
    public NextOfKinDiesEffect copy() {
        return new NextOfKinDiesEffect(this);
    }
}