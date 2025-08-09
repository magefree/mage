package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Effect used to prevent paying life and, optionally, sacrificing permanents as a cost for activated abilities and casting spells.
 * @author Jmlundeen
 */
public class CantPayLifeOrSacrificeAbility extends SimpleStaticAbility {

    private final String rule;

    public CantPayLifeOrSacrificeAbility(FilterPermanent sacrificeFilter) {
        this(false, sacrificeFilter);
    }

    /**
     * @param onlyNonManaAbilities boolean to set if the restriction should only apply to non-mana abilities
     * @param sacrificeFilter filter for types of permanents that cannot be sacrificed, can be null if sacrifice not needed.
     *                        e.g. Karn's Sylex
     */
    public CantPayLifeOrSacrificeAbility(boolean onlyNonManaAbilities, FilterPermanent sacrificeFilter) {
        super(new CantPayLifeEffect(onlyNonManaAbilities));
        if (sacrificeFilter != null) {
            addEffect(new CantSacrificeEffect(onlyNonManaAbilities, sacrificeFilter));
        }
        this.rule = makeRule(onlyNonManaAbilities, sacrificeFilter);
    }

    private CantPayLifeOrSacrificeAbility(CantPayLifeOrSacrificeAbility effect) {
        super(effect);
        this.rule = effect.rule;
    }

    public CantPayLifeOrSacrificeAbility copy() {
        return new CantPayLifeOrSacrificeAbility(this);
    }

    String makeRule(boolean nonManaAbilities, FilterPermanent sacrificeFilter) {
        StringBuilder sb = new StringBuilder("Players can't pay life");
        if (sacrificeFilter != null) {
            sb.append(" or sacrifice ").append(sacrificeFilter.getMessage());
        }
        sb.append(" to cast spells or activate abilities");
        if (nonManaAbilities) {
            sb.append(" that aren't mana abilities");
        }
        sb.append(".");
        return sb.toString();
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class CantPayLifeEffect extends ContinuousEffectImpl {

    private final boolean onlyNonManaAbilities;

    /**
     * @param onlyNonManaAbilities boolean to set if the restriction should only apply to non-mana abilities
     */
    CantPayLifeEffect(boolean onlyNonManaAbilities) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        this.onlyNonManaAbilities = onlyNonManaAbilities;
    }

    private CantPayLifeEffect(CantPayLifeEffect effect) {
        super(effect);
        this.onlyNonManaAbilities = effect.onlyNonManaAbilities;
    }

    public CantPayLifeEffect copy() {
        return new CantPayLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return false;
            }
            player.addPayLifeCostRestriction(Player.PayLifeCostRestriction.CAST_SPELLS);
            if (this.onlyNonManaAbilities) {
                player.addPayLifeCostRestriction(Player.PayLifeCostRestriction.ACTIVATE_NON_MANA_ABILITIES);
            } else {
                player.addPayLifeCostRestriction(Player.PayLifeCostRestriction.ACTIVATE_MANA_ABILITIES);
                player.addPayLifeCostRestriction(Player.PayLifeCostRestriction.ACTIVATE_NON_MANA_ABILITIES);
            }
        }
        return true;
    }
}

class CantSacrificeEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterPermanent sacrificeFilter;
    private final boolean onlyNonManaAbilities;

    CantSacrificeEffect(boolean onlyNonManaAbilities, FilterPermanent sacrificeFilter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.sacrificeFilter = sacrificeFilter;
        this.onlyNonManaAbilities = onlyNonManaAbilities;
    }

    private CantSacrificeEffect(CantSacrificeEffect effect) {
        super(effect);
        this.sacrificeFilter = effect.sacrificeFilter.copy();
        this.onlyNonManaAbilities = effect.onlyNonManaAbilities;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() ==  GameEvent.EventType.PAY_SACRIFICE_COST;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        Optional<Ability> abilityOptional = game.getAbility(UUID.fromString(event.getData()), event.getSourceId());
        if (permanent == null || !abilityOptional.isPresent()) {
            return false;
        }
        Ability abilityWithCost = abilityOptional.get();
        boolean isActivatedAbility = (onlyNonManaAbilities && abilityWithCost.isManaActivatedAbility()) ||
                (!onlyNonManaAbilities && abilityWithCost.isActivatedAbility());
        if (!isActivatedAbility && abilityWithCost.getAbilityType() != AbilityType.SPELL) {
            return false;
        }
        return this.sacrificeFilter.match(permanent, event.getPlayerId(), source, game);
    }

    @Override
    public CantSacrificeEffect copy() {
        return new CantSacrificeEffect(this);
    }
}
