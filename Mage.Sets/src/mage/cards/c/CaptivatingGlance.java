
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class CaptivatingGlance extends CardImpl {

    public CaptivatingGlance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your end step, clash with an opponent. If you win, gain control of enchanted creature. Otherwise, that player gains control of enchanted creature.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new CaptivatingGlanceEffect(), false));

    }

    private CaptivatingGlance(final CaptivatingGlance card) {
        super(card);
    }

    @Override
    public CaptivatingGlance copy() {
        return new CaptivatingGlance(this);
    }
}

class CaptivatingGlanceEffect extends OneShotEffect {

    public CaptivatingGlanceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "clash with an opponent. If you win, gain control of enchanted creature. Otherwise, that player gains control of enchanted creature";
    }

    public CaptivatingGlanceEffect(final CaptivatingGlanceEffect effect) {
        super(effect);
    }

    @Override
    public CaptivatingGlanceEffect copy() {
        return new CaptivatingGlanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final boolean clashResult;
        Player controller = game.getPlayer(source.getControllerId());
        Permanent captivatingGlance = game.getPermanent(source.getSourceId());
        if (controller != null
                && captivatingGlance != null) {
            Permanent enchantedCreature = game.getPermanent(captivatingGlance.getAttachedTo());
            clashResult = ClashEffect.getInstance().apply(game, source);
            if (enchantedCreature != null) {
                if (clashResult) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, false, controller.getId());
                    effect.setTargetPointer(new FixedTarget(enchantedCreature.getId(), game));
                    game.addEffect(effect, source);
                } else {
                    Player opponentWhomControllerClashedWith = game.getPlayer(targetPointer.getFirst(game, source));
                    if (opponentWhomControllerClashedWith != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, false, opponentWhomControllerClashedWith.getId());
                        effect.setTargetPointer(new FixedTarget(enchantedCreature.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
