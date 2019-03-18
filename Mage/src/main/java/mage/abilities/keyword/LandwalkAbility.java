package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LandwalkAbility extends EvasionAbility {

    public LandwalkAbility(FilterLandPermanent filter) {
        this(filter, true);
    }

    public LandwalkAbility(FilterLandPermanent filter, boolean withHintText) {
        this.addEffect(new LandwalkEffect(filter, withHintText));
    }

    public LandwalkAbility(final LandwalkAbility ability) {
        super(ability);
    }

    @Override
    public LandwalkAbility copy() {
        return new LandwalkAbility(this);
    }

    @Override
    public String getRule() {
        String ruleText = super.getRule();
        if (!ruleText.isEmpty() && ruleText.endsWith(".")) {
            return ruleText.substring(0, ruleText.length() - 1);
        }
        return ruleText;
    }

}

class LandwalkEffect extends RestrictionEffect {

    protected FilterLandPermanent filter;

    public LandwalkEffect(FilterLandPermanent filter, boolean withHintText) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = setText(withHintText);
    }

    public LandwalkEffect(final LandwalkEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (game.getBattlefield().contains(filter, blocker.getControllerId(), 1, game)
                && null == game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_LANDWALK, source, blocker.getControllerId(), game)) {
            switch (filter.getMessage()) {
                case "plains":
                    return null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_PLAINSWALK, source, blocker.getControllerId(), game);
                case "island":
                    return null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_ISLANDWALK, source, blocker.getControllerId(), game);
                case "swamp":
                    return null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_SWAMPWALK, source, blocker.getControllerId(), game);
                case "mountain":
                    return null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_MOUNTAINWALK, source, blocker.getControllerId(), game);
                case "forest":
                    return null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_FORESTWALK, source, blocker.getControllerId(), game);
                default:
                    return false;

            }
        }
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public LandwalkEffect copy() {
        return new LandwalkEffect(this);
    }

    private String setText(boolean withHintText) {
        // Swampwalk (This creature can't be blocked as long as defending player controls a Swamp.)
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append("walk");
        if (withHintText) {
            sb.append(" <i>(This creature can't be blocked as long as defending player controls ");
            switch (filter.getMessage()) {
                case "swamp":
                    sb.append("a Swamp");
                    break;
                case "plains":
                    sb.append("a Plains");
                    break;
                case "mountain":
                    sb.append("a Mountain");
                    break;
                case "forest":
                    sb.append("a Forest");
                    break;
                case "island":
                    sb.append("an Island");
                    break;
                default:
                    sb.append("a " + filter.getMessage());

            }
            sb.append(".)</i>");
        }
        return sb.toString();
    }
}
