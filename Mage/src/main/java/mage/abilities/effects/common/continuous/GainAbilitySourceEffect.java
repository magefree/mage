package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilitySourceEffect extends ContinuousEffectImpl {

    protected Ability ability;
    // shall a card gain the ability (otherwise permanent)
    private final boolean onCard;

    /**
     * Add ability with Duration.WhileOnBattlefield
     */
    public GainAbilitySourceEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration) {
        this(ability, duration, false);
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration, boolean onCard) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.onCard = onCard;
        this.staticText = "{this} gains " + CardUtil.stripReminderText(ability.getRule())
                + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
        this.generateGainAbilityDependencies(ability, null);
    }

    protected GainAbilitySourceEffect(final GainAbilitySourceEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.onCard = effect.onCard;
    }

    @Override
    public GainAbilitySourceEffect copy() {
        return new GainAbilitySourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!onCard && Duration.WhileOnBattlefield != duration) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
                return;
            }
        }
        if (affectedObjectsSet) {
            Permanent permanent = game.getPermanentEntering(source.getSourceId());
            if (permanent != null) {
                affectedObjectList.add(new MageObjectReference(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1, game));
            } else {
                affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (onCard) {
            Card card;
            if (affectedObjectsSet) {
                card = affectedObjectList.get(0).getCard(game);
            } else {
                card = game.getCard(source.getSourceId());
            }
            if (card != null) {
                // add ability to card only once
                game.getState().addOtherAbility(card, ability);
                return true;
            }
        } else {
            Permanent permanent;
            if (affectedObjectsSet) {
                permanent = affectedObjectList.get(0).getPermanent(game);
            } else {
                permanent = game.getPermanent(source.getSourceId());
            }
            if (permanent != null) {
                permanent.addAbility(ability, source.getSourceId(), game);
                return true;
            }
        }
        if (duration == Duration.Custom) {
            this.discard();
        }
        return true;
    }
}
