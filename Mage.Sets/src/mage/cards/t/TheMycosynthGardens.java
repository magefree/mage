package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XCMCPermanentAdjuster;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMycosynthGardens extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("nontoken artifact you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public TheMycosynthGardens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SPHERE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {X}, {T}: The Mycosynth Gardens becomes a copy of target nontoken artifact you control with mana value X.
        ability = new SimpleActivatedAbility(new TheMycosynthGardensCopyEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(XCMCPermanentAdjuster.instance);
        this.addAbility(ability);
    }

    private TheMycosynthGardens(final TheMycosynthGardens card) {
        super(card);
    }

    @Override
    public TheMycosynthGardens copy() {
        return new TheMycosynthGardens(this);
    }
}

class TheMycosynthGardensCopyEffect extends OneShotEffect {

    public TheMycosynthGardensCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of target nontoken artifact you control with mana value X.";
    }

    public TheMycosynthGardensCopyEffect(final TheMycosynthGardensCopyEffect effect) {
        super(effect);
    }

    @Override
    public TheMycosynthGardensCopyEffect copy() {
        return new TheMycosynthGardensCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            return true;
        }
        return false;
    }
}