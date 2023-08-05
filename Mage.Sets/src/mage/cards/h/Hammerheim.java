
package mage.cards.h;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.LandwalkAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Hammerheim extends CardImpl {

    public Hammerheim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {tap}: Target creature loses all landwalk abilities until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HammerheimEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Hammerheim(final Hammerheim card) {
        super(card);
    }

    @Override
    public Hammerheim copy() {
        return new Hammerheim(this);
    }
}

class HammerheimEffect extends ContinuousEffectImpl {

    public HammerheimEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.staticText = "Target creature loses all landwalk abilities until end of turn.";
    }

    public HammerheimEffect(final HammerheimEffect effect) {
        super(effect);
    }

    @Override
    public HammerheimEffect copy() {
        return new HammerheimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            for (Iterator<Ability> iter = permanent.getAbilities().iterator(); iter.hasNext();) {
                Ability ab = iter.next();
                if (ab instanceof LandwalkAbility) {
                    iter.remove();
                }
            }
        }
        return true;
    }
}
