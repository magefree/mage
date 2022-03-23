package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class HeraldicBanner extends CardImpl {

    public HeraldicBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Heraldic Banner enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Creatures you control of the chosen color get +1/+0.
        this.addAbility(new SimpleStaticAbility(new HeraldicBannerEffect()));

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private HeraldicBanner(final HeraldicBanner card) {
        super(card);
    }

    @Override
    public HeraldicBanner copy() {
        return new HeraldicBanner(this);
    }
}

class HeraldicBannerEffect extends ContinuousEffectImpl {

    HeraldicBannerEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen color get +1/+0";
    }

    private HeraldicBannerEffect(final HeraldicBannerEffect effect) {
        super(effect);
    }

    @Override
    public HeraldicBannerEffect copy() {
        return new HeraldicBannerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
        if (color == null) {
            return false;
        }
        for (Permanent perm : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            if (perm.getColor(game).contains(color)) {
                perm.addPower(1);
            }
        }
        return true;
    }
}
