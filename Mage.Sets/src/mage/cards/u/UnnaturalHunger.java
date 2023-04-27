package mage.cards.u;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class UnnaturalHunger extends CardImpl {

    public UnnaturalHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, Unnatural Hunger deals damage to that player equal to that creature's power unless they sacrifice another creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new UnnaturalHungerEffect(),
                TargetController.CONTROLLER_ATTACHED_TO, false));
    }

    private UnnaturalHunger(final UnnaturalHunger card) {
        super(card);
    }

    @Override
    public UnnaturalHunger copy() {
        return new UnnaturalHunger(this);
    }
}

class UnnaturalHungerEffect extends OneShotEffect {

    public UnnaturalHungerEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals damage to that player equal to that creature's power unless they sacrifice another creature";
    }

    private UnnaturalHungerEffect(UnnaturalHungerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura != null) {
            Permanent attachedTo = game.getPermanent(aura.getAttachedTo());
            if (attachedTo != null) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                filter.add(Predicates.not(new PermanentIdPredicate(aura.getAttachedTo())));  // not attached permanent
                Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter));
                Player enchantedCreatureController = game.getPlayer(attachedTo.getControllerId());
                if (enchantedCreatureController != null
                        && cost.canPay(source, source, enchantedCreatureController.getId(), game)
                        && enchantedCreatureController.chooseUse(outcome, "Sacrifice another creature to prevent " + attachedTo.getPower().getValue() + " damage?", source, game)
                        && cost.pay(source, game, source, enchantedCreatureController.getId(), true)) {
                }
                if (enchantedCreatureController != null
                        && !cost.isPaid()) {
                    enchantedCreatureController.damage(attachedTo.getPower().getValue(), source.getSourceId(), source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public UnnaturalHungerEffect copy() {
        return new UnnaturalHungerEffect(this);
    }
}
