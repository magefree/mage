package mage.cards.m;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class MindWhip extends CardImpl {

    public MindWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, that player may pay {3}. If they don't, Mind Whip deals 2 damage to that player and you tap that creature.
        Effect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new MindWhipEffect(),
                new ManaCostsImpl<>("{3}"),
                "");
        effect.setText("that player may pay {3}. If they don't, {this} deals 2 damage to that player and you tap that creature.");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                effect,
                TargetController.CONTROLLER_ATTACHED_TO, false));

    }

    private MindWhip(final MindWhip card) {
        super(card);
    }

    @Override
    public MindWhip copy() {
        return new MindWhip(this);
    }
}

class MindWhipEffect extends OneShotEffect {

    public MindWhipEffect() {
        super(Outcome.Neutral);
        staticText = "";
    }

    public MindWhipEffect(final MindWhipEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controllerOfEnchantedCreature = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent mindWhip = game.getPermanent(source.getSourceId());
        if (controllerOfEnchantedCreature != null
                && mindWhip != null) {
            Permanent enchantedCreature = game.getPermanent(mindWhip.getAttachedTo());
            if (enchantedCreature != null) {
                Effect effect = new DamageTargetEffect(2);
                effect.setTargetPointer(new FixedTarget(controllerOfEnchantedCreature.getId()));
                effect.apply(game, source);
                enchantedCreature.tap(source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public MindWhipEffect copy() {
        return new MindWhipEffect(this);
    }

}
