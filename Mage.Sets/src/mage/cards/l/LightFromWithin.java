
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class LightFromWithin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LightFromWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");


        // Chroma - Each creature you control gets +1/+1 for each white mana symbol in its mana cost.
        Effect effect = new LightFromWithinEffect();
        effect.setText("<i>Chroma</i> &mdash; Each creature you control gets +1/+1 for each white mana symbol in its mana cost.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

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

    boolean boosted = false;

    public LightFromWithinEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
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
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
            if (creature != null) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    creature.addPower(new ChromaLightFromWithinCount(creature).calculate(game, source, this));
                    creature.addToughness(new ChromaLightFromWithinCount(creature).calculate(game, source, this));
                    boosted = true;
                }
            }
        }
        return boosted;
    }
}

class ChromaLightFromWithinCount implements DynamicValue {

    private Permanent permanent;

    public ChromaLightFromWithinCount(Permanent permanent) {
        this.permanent = permanent;
    }

    public ChromaLightFromWithinCount(final ChromaLightFromWithinCount dynamicValue) {
        this.permanent = dynamicValue.permanent;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int chroma = 0;
        chroma += permanent.getManaCost().getMana().getWhite();
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaLightFromWithinCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
