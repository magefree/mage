package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class SigilOfSleep extends CardImpl {

    public SigilOfSleep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals damage to a player, return target creature that player controls to its owner's hand.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return target creature that player controls to its owner's hand");
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(effect, "enchanted", false, true, false);
        ability.setTargetAdjuster(SigilOfSleepAdjuster.instance);
        this.addAbility(ability);
    }

    private SigilOfSleep(final SigilOfSleep card) {
        super(card);
    }

    @Override
    public SigilOfSleep copy() {
        return new SigilOfSleep(this);
    }
}

enum SigilOfSleepAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID playerId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        if (playerId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that player controls");
            filter.add(new ControllerIdPredicate(playerId));
            Target target = new TargetCreaturePermanent(filter);
            ability.getTargets().clear();
            ability.addTarget(target);
            ability.getEffects().get(0).setTargetPointer(new FirstTargetPointer());
        }
    }
}
