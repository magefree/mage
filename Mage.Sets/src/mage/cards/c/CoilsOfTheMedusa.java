package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author arcox
 */
public final class CoilsOfTheMedusa extends CardImpl {
    public CoilsOfTheMedusa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, -1, Duration.WhileOnBattlefield)));

        // Sacrifice Coils of the Medusa: Destroy all non-Wall creatures blocking enchanted creature.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CoilsOfTheMedusaDestroyEffect(),
                new SacrificeSourceCost())
        );
    }

    private CoilsOfTheMedusa(final CoilsOfTheMedusa card) {
        super(card);
    }

    @Override
    public CoilsOfTheMedusa copy() {
        return new CoilsOfTheMedusa(this);
    }
}

class CoilsOfTheMedusaDestroyEffect extends OneShotEffect {
    public CoilsOfTheMedusaDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all non-Wall creatures blocking enchanted creature.";
    }

    public CoilsOfTheMedusaDestroyEffect(final CoilsOfTheMedusaDestroyEffect effect) {
        super(effect);
    }

    @Override
    public CoilsOfTheMedusaDestroyEffect copy() {
        return new CoilsOfTheMedusaDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent coils = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        List<UUID> blockers = new ArrayList<>();

        if (coils != null && coils.getAttachedTo() != null) {
            // grab all creatures blocking enchanted creature
            Permanent enchantedCreature = game.getPermanent(coils.getAttachedTo());
            if (enchantedCreature != null && enchantedCreature.isBlocked(game)) {
                for (CombatGroup group : game.getCombat().getGroups()) {
                    if (group.getAttackers().contains(enchantedCreature.getId())) {
                        blockers = group.getBlockers();
                        break;
                    }
                }

                // filter out defenders, destroying the rest
                while (!blockers.isEmpty()) {
                    Permanent blocker = game.getPermanent(blockers.remove(0));
                    if (!blocker.hasAbility(DefenderAbility.getInstance(), game)) {
                        blocker.destroy(source, game, false);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
