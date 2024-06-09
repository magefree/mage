
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class ObeliskOfUrd extends CardImpl {

    public ObeliskOfUrd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // As Obelisk of Urd enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ObeliskOfUrdBoostEffect()));
    }

    private ObeliskOfUrd(final ObeliskOfUrd card) {
        super(card);
    }

    @Override
    public ObeliskOfUrd copy() {
        return new ObeliskOfUrd(this);
    }
}

class ObeliskOfUrdBoostEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    ObeliskOfUrdBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen type get +2/+2.";
    }

    private ObeliskOfUrdBoostEffect(final ObeliskOfUrdBoostEffect effect) {
        super(effect);
    }

    @Override
    public ObeliskOfUrdBoostEffect copy() {
        return new ObeliskOfUrdBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (subType != null) {
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (perm.hasSubtype(subType, game)) {
                        perm.addPower(2);
                        perm.addToughness(2);
                    }
                }
            } else {
                discard();
            }
        }
        return true;
    }
}
