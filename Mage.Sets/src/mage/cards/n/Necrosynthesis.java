package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class Necrosynthesis extends CardImpl {

    public Necrosynthesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has "Whenever another creature dies, put a +1/+1 counter on this creature."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(
                        new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true), AttachmentType.AURA
                ))
        );

        // When enchanted creature dies, look at the top X cards of your library, where X is its power. Put one of those cards into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new DiesAttachedTriggeredAbility(new NecrosynthesisEffect(), "enchanted creature"));
    }

    private Necrosynthesis(final Necrosynthesis card) {
        super(card);
    }

    @Override
    public Necrosynthesis copy() {
        return new Necrosynthesis(this);
    }
}

class NecrosynthesisEffect extends OneShotEffect {

    public NecrosynthesisEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top X cards of your library, where X is its power." +
                "Put one of those cards into your hand and the rest on the bottom of your library in a random order.";
    }

    public NecrosynthesisEffect(final mage.cards.n.NecrosynthesisEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.n.NecrosynthesisEffect copy() {
        return new mage.cards.n.NecrosynthesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Permanent creature = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);

            int power = creature.getPower().getValue();
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, power));
            controller.lookAtCards(source, null, cards, game);
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
            if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                Cards targetCards = new CardsImpl(target.getTargets());
                controller.moveCards(targetCards, Zone.HAND, source, game);
                cards.removeAll(targetCards);
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}

