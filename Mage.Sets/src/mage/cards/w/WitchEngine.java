
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class WitchEngine extends CardImpl {

    public WitchEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // {T}: Add {B}{B}{B}{B}. Target opponent gains control of Witch Engine. (Activate this ability only any time you could cast an instant.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(4)), new TapSourceCost());
        ability.addEffect(new WitchEngineEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private WitchEngine(final WitchEngine card) {
        super(card);
    }

    @Override
    public WitchEngine copy() {
        return new WitchEngine(this);
    }
}

class WitchEngineEffect extends ContinuousEffectImpl {

    public WitchEngineEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public WitchEngineEffect(final WitchEngineEffect effect) {
        super(effect);
    }

    @Override
    public WitchEngineEffect copy() {
        return new WitchEngineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        } else {
            discard();
        }
        return false;
    }

}
