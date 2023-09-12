
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author LevelX2
 */
public final class ThespiansStage extends CardImpl {

    public ThespiansStage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // 2, {T}: Thespian's Stage becomes a copy of target land, except it has this ability.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThespiansStageCopyEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

    }

    private ThespiansStage(final ThespiansStage card) {
        super(card);
    }

    @Override
    public ThespiansStage copy() {
        return new ThespiansStage(this);
    }
}

class ThespiansStageCopyEffect extends OneShotEffect {

    public ThespiansStageCopyEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} becomes a copy of target land, except it has this ability";
    }

    private ThespiansStageCopyEffect(final ThespiansStageCopyEffect effect) {
        super(effect);
    }

    @Override
    public ThespiansStageCopyEffect copy() {
        return new ThespiansStageCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            Permanent newPermanent = game.copyPermanent(copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThespiansStageCopyEffect(), new GenericManaCost(2));
            ability.addCost(new TapSourceCost());
            ability.addTarget(new TargetLandPermanent());
            newPermanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
