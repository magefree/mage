package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEverChangingDane extends CardImpl {

    public TheEverChangingDane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice another creature: The Ever-Changing 'Dane becomes a copy of the sacrificed creature, except it has this ability.
        this.addAbility(makeAbility());
    }

    private TheEverChangingDane(final TheEverChangingDane card) {
        super(card);
    }

    @Override
    public TheEverChangingDane copy() {
        return new TheEverChangingDane(this);
    }

    static Ability makeAbility() {
        Ability ability = new SimpleActivatedAbility(new TheEverChangingDaneEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        return ability;
    }
}

class TheEverChangingDaneEffect extends OneShotEffect {

    TheEverChangingDaneEffect() {
        super(Outcome.Benefit);
        staticText = "{this} becomes a copy of the sacrificed creature, except it has this ability";
    }

    private TheEverChangingDaneEffect(final TheEverChangingDaneEffect effect) {
        super(effect);
    }

    @Override
    public TheEverChangingDaneEffect copy() {
        return new TheEverChangingDaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Permanent sacrificedPermanent = CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .filter(Objects::nonNull)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findAny()
                .orElse(null);
        if (sacrificedPermanent == null) {
            return false;
        }
        game.copyPermanent(sacrificedPermanent, permanent.getId(), source, new TheEverChangingDaneCopyApplier());
        return true;
    }
}

class TheEverChangingDaneCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(TheEverChangingDane.makeAbility());
        return true;
    }
}
