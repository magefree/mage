
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class MirrorGallery extends CardImpl {

    public MirrorGallery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // The "legend rule" doesn't apply.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MirrorGalleryRuleEffect()));
    }

    private MirrorGallery(final MirrorGallery card) {
        super(card);
    }

    @Override
    public MirrorGallery copy() {
        return new MirrorGallery(this);
    }
}

class MirrorGalleryRuleEffect extends ContinuousEffectImpl {

    public MirrorGalleryRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "The \"legend rule\" doesn't apply";
    }

    public MirrorGalleryRuleEffect(final MirrorGalleryRuleEffect effect) {
        super(effect);
    }

    @Override
    public MirrorGalleryRuleEffect copy() {
        return new MirrorGalleryRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        game.getState().setLegendaryRuleActive(false);
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
