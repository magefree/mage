package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileSourceReplacementEffect;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * @author BetaSteward_at_googlemail.com
 * <p>
 * <p>
 * 702.82. Unearth
 * <p>
 * 702.82a Unearth is an activated ability that functions while the card with
 * unearth is in a graveyard. "Unearth [cost]" means "[Cost]: Return this card
 * from your graveyard to the battlefield. It gains haste. Exile it at the
 * beginning of the next end step. If it would leave the battlefield, exile it
 * instead of putting it anywhere else. Activate this ability only any time you
 * could cast a sorcery."
 */
public class UnearthAbility extends ActivatedAbilityImpl {

    public UnearthAbility(Cost costs) {
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false, true, true)
                .withAdditionalEffect(new CreateDelayedTriggeredAbilityEffect(
                        new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ExileSourceEffect())
                ))
                // a replacement effect of the ability, not an ability the permanent gains, so losing all abilities doesn't stop it
                .withAdditionalEffect(new LeaveBattlefieldExileSourceReplacementEffect("it").setDuration(Duration.Custom)), costs);
        this.timing = TimingRule.SORCERY;
    }

    protected UnearthAbility(final UnearthAbility ability) {
        super(ability);
    }

    @Override
    public UnearthAbility copy() {
        return new UnearthAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Unearth ").append(this.getManaCosts().getText());
        sb.append(" <i>(").append(this.getManaCosts().getText());
        sb.append(": Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)</i>");
        return sb.toString();
    }

}
