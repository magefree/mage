package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.RandomUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author xenohedron
 */

public final class InfectiousRage extends CardImpl {

    public InfectiousRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, -1)));
        
        // When enchanted creature dies, choose a creature at random Infectious Rage can enchant.
        // Return Infectious Rage to the battlefield attached to that creature.
        this.addAbility(new DiesAttachedTriggeredAbility(new InfectiousRageReattachEffect(), "enchanted creature"));
        
    }

    private InfectiousRage(final InfectiousRage card) {
        super(card);
    }

    @Override
    public InfectiousRage copy() {
        return new InfectiousRage(this);
    }
}

class InfectiousRageReattachEffect extends OneShotEffect {

    public InfectiousRageReattachEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "choose a creature at random {this} can enchant. Return {this} to the battlefield attached to that creature.";
    }

    public InfectiousRageReattachEffect(final InfectiousRageReattachEffect effect) {
        super(effect);
    }

    @Override
    public InfectiousRageReattachEffect copy() {
        return new InfectiousRageReattachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        Card auraCard = game.getCard(source.getSourceId());
        if (controller == null || auraCard == null) {
            return false;
        }
        if (source.getSourceObjectZoneChangeCounter() != auraCard.getZoneChangeCounter(game)) {
            return false;
        }

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new CanBeEnchantedByPredicate(auraCard));
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);

        if (permanents.isEmpty()) {
            game.informPlayers("No valid creatures for " + auraCard.getLogName() + "to enchant.");
            return false;
        }
        Permanent creature = RandomUtil.randomFromCollection(permanents);
        if (creature == null) {
            return false;
        }
        game.getState().setValue("attachTo:" + auraCard.getId(), creature);
        controller.moveCards(auraCard, Zone.BATTLEFIELD, source, game);
        return creature.addAttachment(auraCard.getId(), source, game);

    }

}
