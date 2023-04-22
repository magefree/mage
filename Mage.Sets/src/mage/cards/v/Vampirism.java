
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author tcontis
 */
public final class Vampirism extends CardImpl {

    public Vampirism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Vampirism enters the battlefield, draw a card at the beginning of the next turn's upkeep.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse))
                .setText("draw a card at the beginning of the next turn's upkeep"), false));

        // Enchanted creature gets +1/+1 for each other creature you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VampirismBoostEnchantedEffect()));

        // Other creatures you control get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(-1, -1, Duration.WhileOnBattlefield, true)));

    }

    private Vampirism(final Vampirism card) {
        super(card);
    }

    @Override
    public Vampirism copy() {
        return new Vampirism(this);
    }
}

class VampirismBoostEnchantedEffect extends ContinuousEffectImpl {

    public VampirismBoostEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Enchanted creature gets +1/+1 for each other creature you control";
    }

    public VampirismBoostEnchantedEffect(final VampirismBoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public VampirismBoostEnchantedEffect copy() {
        return new VampirismBoostEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) - 1;
        if (count > 0) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent creature = game.getPermanent(enchantment.getAttachedTo());
                if (creature != null) {
                    creature.addPower(count);
                    creature.addToughness(count);
                    return true;
                }
            }
        }
        return false;
    }
}
