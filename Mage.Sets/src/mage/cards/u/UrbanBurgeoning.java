package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class UrbanBurgeoning extends CardImpl {

    static final String rule = "Enchanted land has \"Untap this land during each other player's untap step.\"";

    public UrbanBurgeoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land has "Untap this land during each other player's untap step."
        Ability gainedAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new UrbanBurgeoningUntapEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield, rule)));
    }

    private UrbanBurgeoning(final UrbanBurgeoning card) {
        super(card);
    }

    @Override
    public UrbanBurgeoning copy() {
        return new UrbanBurgeoning(this);
    }
}

class UrbanBurgeoningUntapEffect extends ContinuousEffectImpl {

    public UrbanBurgeoningUntapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Untap this land during each other player's untap step";
    }

    public UrbanBurgeoningUntapEffect(final UrbanBurgeoningUntapEffect effect) {
        super(effect);
    }

    @Override
    public UrbanBurgeoningUntapEffect copy() {
        return new UrbanBurgeoningUntapEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean applied = Boolean.TRUE.equals(game.getState().getValue(source.getSourceId() + "applied"));
        if (!applied && layer == Layer.RulesEffects) {
            if (!game.isActivePlayer(source.getControllerId()) && game.getStep().getType() == PhaseStep.UNTAP) {
                game.getState().setValue(source.getSourceId() + "applied", true);
                Permanent land = game.getPermanent(source.getSourceId());
                boolean untap = true;
                for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(land, game).keySet()) {
                    untap &= effect.canBeUntapped(land, source, game, true);
                }
                if (untap) {
                    land.untap(game);
                }
            }
        } else if (applied && layer == Layer.RulesEffects) {
            if (game.getStep().getType() == PhaseStep.END_TURN) {
                game.getState().setValue(source.getSourceId() + "applied", false);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
