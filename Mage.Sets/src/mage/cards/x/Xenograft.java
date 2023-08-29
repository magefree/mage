
package mage.cards.x;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class Xenograft extends CardImpl {

    public Xenograft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // As Xenograft enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Detriment)));
        // Each creature you control is the chosen type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new XenograftAddSubtypeEffect()));
    }

    private Xenograft(final Xenograft card) {
        super(card);
    }

    @Override
    public Xenograft copy() {
        return new Xenograft(this);
    }
}

class XenograftAddSubtypeEffect extends ContinuousEffectImpl {

    public XenograftAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Each creature you control is the chosen type in addition to its other types";
    }

    private XenograftAddSubtypeEffect(final XenograftAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.addSubType(game, subType);
            }
        }
        return true;
    }

    @Override
    public XenograftAddSubtypeEffect copy() {
        return new XenograftAddSubtypeEffect(this);
    }
}
