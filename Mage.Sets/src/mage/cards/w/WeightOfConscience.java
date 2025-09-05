package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class WeightOfConscience extends CardImpl {

    public WeightOfConscience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(new CantAttackAttachedEffect(AttachmentType.AURA)));

        // Tap two untapped creatures you control that share a creature type: Exile enchanted creature.
        this.addAbility(new SimpleActivatedAbility(new ExileAttachedEffect(), new TapTargetCost(new WeightOfConscienceTarget())));
    }

    private WeightOfConscience(final WeightOfConscience card) {
        super(card);
    }

    @Override
    public WeightOfConscience copy() {
        return new WeightOfConscience(this);
    }
}

class WeightOfConscienceTarget extends TargetControlledPermanent {

    private static final FilterControlledPermanent filterUntapped = new FilterControlledCreaturePermanent("untapped creatures you control that share a creature type");

    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    WeightOfConscienceTarget() {
        super(2, 2, filterUntapped, true);
    }

    private WeightOfConscienceTarget(final WeightOfConscienceTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return possibleTargets;
        }

        if (this.getTargets().isEmpty()) {
            // choosing first target - use any permanent with shared types
            List<Permanent> permanentList = game.getBattlefield().getActivePermanents(filterUntapped, sourceControllerId, source, game);
            if (permanentList.size() < 2) {
                return possibleTargets;
            }
            for (Permanent permanent : permanentList) {
                if (permanent.isAllCreatureTypes(game)) {
                    possibleTargets.add(permanent.getId());
                    continue;
                }
                FilterPermanent filter = filterUntapped.copy();
                filter.add(new SharesCreatureTypePredicate(permanent));
                if (game.getBattlefield().count(filter, sourceControllerId, source, game) > 1) {
                    possibleTargets.add(permanent.getId());
                }
            }
        } else {
            // choosing second target - must have shared type with first target
            Permanent firstTargetCreature = game.getPermanent(this.getFirstTarget());
            if (firstTargetCreature == null) {
                return possibleTargets;
            }
            FilterPermanent filter = filterUntapped.copy();
            filter.add(new SharesCreatureTypePredicate(firstTargetCreature));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterUntapped, sourceControllerId, source, game)) {
                if (permanent != null) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public WeightOfConscienceTarget copy() {
        return new WeightOfConscienceTarget(this);
    }
}
