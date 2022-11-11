
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 * @author duncant
 */
public final class Shapesharer extends CardImpl {

    private static final FilterPermanent filterShapeshifter = new FilterPermanent("Shapeshifter");

    static {
        filterShapeshifter.add(SubType.SHAPESHIFTER.getPredicate());
    }

    public Shapesharer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new ChangelingAbility());

        // {2}{U}: Target Shapeshifter becomes a copy of target creature until your next turn.
        Ability copyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ShapesharerEffect(),
                new ManaCostsImpl<>("{2}{U}"));
        copyAbility.addTarget(new TargetPermanent(filterShapeshifter));
        copyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(copyAbility);
    }

    private Shapesharer(final Shapesharer card) {
        super(card);
    }

    @Override
    public Shapesharer copy() {
        return new Shapesharer(this);
    }
}

class ShapesharerEffect extends OneShotEffect {

    public ShapesharerEffect() {
        super(Outcome.Copy);
        this.staticText = "Target Shapeshifter becomes a copy of target creature until your next turn.";
    }

    public ShapesharerEffect(final ShapesharerEffect effect) {
        super(effect);
    }

    @Override
    public ShapesharerEffect copy() {
        return new ShapesharerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Permanent copyTo = game.getPermanent(getTargetPointer().getFirst(game, ability));
        if (copyTo != null) {
            Permanent copyFrom = game.getPermanentOrLKIBattlefield(ability.getTargets().get(1).getFirstTarget());
            if (copyFrom != null) {
                game.copyPermanent(Duration.UntilYourNextTurn, copyFrom, copyTo.getId(), ability, new EmptyCopyApplier());
            }
        }
        return true;
    }
}
