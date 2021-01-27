
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorOfTheForebears extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public MirrorOfTheForebears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Mirror of the Forebears enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Copy)));

        // 1: Until end of turn, Mirror of the Forebears becomes a copy of target creature you control of the chosen type, except it's an artifact in addition to its other types.
        Ability ability = new SimpleActivatedAbility(new MirrorOfTheForebearsCopyEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MirrorOfTheForebears(final MirrorOfTheForebears card) {
        super(card);
    }

    @Override
    public MirrorOfTheForebears copy() {
        return new MirrorOfTheForebears(this);
    }
}

class MirrorOfTheForebearsCopyEffect extends OneShotEffect {

    MirrorOfTheForebearsCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "until end of turn, {this} becomes a copy of target creature you control of the chosen type, except it's an artifact in addition to its other types";
    }

    private MirrorOfTheForebearsCopyEffect(final MirrorOfTheForebearsCopyEffect effect) {
        super(effect);
    }

    @Override
    public MirrorOfTheForebearsCopyEffect copy() {
        return new MirrorOfTheForebearsCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            game.addEffect(new AddCardTypeSourceEffect(Duration.EndOfTurn, CardType.ARTIFACT), source);
            return true;
        }
        return false;
    }
}
