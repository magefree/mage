
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class KnightOfNewAlara extends CardImpl {

    public KnightOfNewAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);



        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each other multicolored creature you control gets +1/+1 for each of its colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KnightOfNewAlaraEffect()));

    }

    private KnightOfNewAlara(final KnightOfNewAlara card) {
        super(card);
    }

    @Override
    public KnightOfNewAlara copy() {
        return new KnightOfNewAlara(this);
    }
}

class KnightOfNewAlaraEffect extends ContinuousEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public KnightOfNewAlaraEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Each other multicolored creature you control gets +1/+1 for each of its colors";
    }

    private KnightOfNewAlaraEffect(final KnightOfNewAlaraEffect effect) {
        super(effect);
    }

    @Override
    public KnightOfNewAlaraEffect copy() {
        return new KnightOfNewAlaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {            
            if (creature != null && !creature.getId().equals(source.getSourceId())) {
                int colors = creature.getColor(game).getColorCount();
                creature.addPower(colors);
                creature.addToughness(colors);
            }
        }
        return true;
    }
}
