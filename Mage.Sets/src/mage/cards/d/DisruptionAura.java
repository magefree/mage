
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
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

/**
 *
 * @author Plopman
 */
public final class DisruptionAura extends CardImpl {

    public DisruptionAura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted artifact has "At the beginning of your upkeep, sacrifice this artifact unless you pay its mana cost."
        ability = new BeginningOfUpkeepTriggeredAbility(new DisruptionAuraEffect(), TargetController.YOU, false);
        Effect effect = new GainAbilityAttachedEffect(ability, AttachmentType.AURA);
        effect.setText("Enchanted artifact has \"At the beginning of your upkeep, sacrifice this artifact unless you pay its mana cost.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private DisruptionAura(final DisruptionAura card) {
        super(card);
    }

    @Override
    public DisruptionAura copy() {
        return new DisruptionAura(this);
    }
}

class DisruptionAuraEffect extends OneShotEffect {

    public DisruptionAuraEffect() {
        super(Outcome.Sacrifice);
        staticText =  "sacrifice this artifact unless you pay its mana cost";
     }

    public DisruptionAuraEffect(final DisruptionAuraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && permanent != null) {
            
            String message = CardUtil.replaceSourceName("Pay {this} mana cost ?", permanent.getLogName());
            Cost cost = permanent.getManaCost().copy();
            if (player.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            permanent.sacrifice(source, game);
            return true;
        }
        return false;
    }

    @Override
    public DisruptionAuraEffect copy() {
        return new DisruptionAuraEffect(this);
    }

 }
