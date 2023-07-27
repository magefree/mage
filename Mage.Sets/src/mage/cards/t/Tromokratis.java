package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EvasionEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Tromokratis extends CardImpl {

    public Tromokratis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Tromokratis has hexproof unless it's attacking or blocking.
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                new InvertCondition(new SourceMatchesFilterCondition(new FilterAttackingOrBlockingCreature())),
                "{this} has hexproof unless it's attacking or blocking");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Tromokratis can't be blocked unless all creatures defending player controls block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedUnlessAllEffect()));
    }

    private Tromokratis(final Tromokratis card) {
        super(card);
    }

    @Override
    public Tromokratis copy() {
        return new Tromokratis(this);
    }
}

class CantBeBlockedUnlessAllEffect extends EvasionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    CantBeBlockedUnlessAllEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticCantBeBlockedMessage = "can't be blocked unless all creatures defending player controls block it";
        staticText = "{this} " + this.staticCantBeBlockedMessage;
    }

    private CantBeBlockedUnlessAllEffect(final CantBeBlockedUnlessAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        // check if all creatures of defender are able to block this permanent
        // permanent.canBlock() can't be used because causing recursive call
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, blocker.getControllerId(), game)) {
            if (permanent.isTapped() && null == game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, null, blocker.getControllerId(), game)) {
                return true;
            }
            // check blocker restrictions
            for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).entrySet()) {
                for (Ability ability : entry.getValue()) {
                    if (!entry.getKey().canBlock(attacker, permanent, ability, game, canUseChooseDialogs)) {
                        return true;
                    }
                }
            }
            // check also attacker's evasion effects
            for (Map.Entry<EvasionEffect, Set<Ability>> evasionEntry : game.getContinuousEffects().getApplicableEvasionEffects(attacker, game).entrySet()) {
                for (Ability ability : evasionEntry.getValue()) {
                    if (!(evasionEntry.getKey() instanceof CantBeBlockedUnlessAllEffect)
                        && evasionEntry.getKey().cantBeBlocked(attacker, permanent, ability, game, canUseChooseDialogs)) {
                        return true;
                    }
                }
            }
            if (attacker.hasProtectionFrom(permanent, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cantBeBlockedCheckAfter(Permanent attacker, Ability source, Game game, boolean canUseChooseDialogs) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(source.getSourceId())) {
                for (UUID blockerId : combatGroup.getBlockers()) {
                    Permanent blockingCreature = game.getPermanent(blockerId);
                    if (blockingCreature != null) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, blockingCreature.getControllerId(), game)) {
                            if (!combatGroup.getBlockers().contains(permanent.getId())) {
                                // not all creatures block Tromokratis
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CantBeBlockedUnlessAllEffect copy() {
        return new CantBeBlockedUnlessAllEffect(this);
    }
}
