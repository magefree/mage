package mage.cards.p;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author nick.myers
 */
public final class PowerArtifact extends CardImpl {

    public PowerArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted artifact's activated abilities cost less to activate.
        // This effect can't reduce the amount of mana an ability costs to activate to less than one mana.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PowerArtifactCostModificationEffect()));
    }

    private PowerArtifact(final PowerArtifact card) {
        super(card);
    }

    @Override
    public PowerArtifact copy() {
        return new PowerArtifact(this);
    }
}

class PowerArtifactCostModificationEffect extends CostModificationEffectImpl {

    PowerArtifactCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Enchanted artifact's activated abilities cost {2} less to activate. "
                + "This effect can't reduce the amount of mana an ability costs to activate to less than one mana.";

    }

    private PowerArtifactCostModificationEffect(PowerArtifactCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            int reduce = mana.getGeneric();
            if (reduce > 0 && mana.count() == mana.getGeneric()) {
                reduce--;
            }
            if (reduce > 2) {
                reduce = 2;
            }
            CardUtil.reduceCost(abilityToModify, reduce);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Permanent artifact = game.getPermanentOrLKIBattlefield(abilityToModify.getSourceId());
        if (artifact != null
                && artifact.getAttachments().contains(source.getSourceId())) {
            if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                    || (abilityToModify.getAbilityType() == AbilityType.MANA
                    && (abilityToModify instanceof ActivatedAbility))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PowerArtifactCostModificationEffect copy() {
        return new PowerArtifactCostModificationEffect(this);
    }

}
