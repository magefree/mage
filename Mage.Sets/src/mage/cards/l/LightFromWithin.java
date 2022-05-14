package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author awjackson
 *
 */
public final class LightFromWithin extends CardImpl {

    public LightFromWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Chroma - Each creature you control gets +1/+1 for each white mana symbol in its mana cost.
        this.addAbility(new SimpleStaticAbility(new LightFromWithinEffect()).setAbilityWord(AbilityWord.CHROMA));
    }

    private LightFromWithin(final LightFromWithin card) {
        super(card);
    }

    @Override
    public LightFromWithin copy() {
        return new LightFromWithin(this);
    }
}

class LightFromWithinEffect extends ContinuousEffectImpl {

    public LightFromWithinEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Each creature you control gets +1/+1 for each white mana symbol in its mana cost";
    }

    public LightFromWithinEffect(final LightFromWithinEffect effect) {
        super(effect);
    }

    @Override
    public LightFromWithinEffect copy() {
        return new LightFromWithinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), game)) {
            int chroma = creature.getManaCost().getMana().getWhite();
            if (chroma > 0) {
                creature.addPower(chroma);
                creature.addToughness(chroma);
            }
        }
        return true;
    }
}
