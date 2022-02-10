package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.emblems.ChandraAwakenedInfernoEmblem;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraAwakenedInferno extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Elemental creature");

    static {
        filter.add(Predicates.not(SubType.ELEMENTAL.getPredicate()));
    }

    public ChandraAwakenedInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(6);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // +2: Each opponent gets an emblem with "At the beginning of your upkeep, this emblem deals 1 damage to you."
        this.addAbility(new LoyaltyAbility(new ChandraAwakenedInfernoEffect(), 2));

        // -3: Chandra, Awakened Inferno deals 3 damage to each non-Elemental creature.
        this.addAbility(new LoyaltyAbility(new DamageAllEffect(3, filter), -3));

        // -X: Chandra, Awakened Inferno deals X damage to target creature or planeswalker. If a permanent dealt damage this way would die this turn, exile it instead.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(GetXLoyaltyValue.instance));
        ability.addEffect(
                new ExileTargetIfDiesEffect()
                        .setText("If a permanent dealt damage this way would die this turn, exile it instead.")
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private ChandraAwakenedInferno(final ChandraAwakenedInferno card) {
        super(card);
    }

    @Override
    public ChandraAwakenedInferno copy() {
        return new ChandraAwakenedInferno(this);
    }
}

class ChandraAwakenedInfernoEffect extends OneShotEffect {

    ChandraAwakenedInfernoEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent gets an emblem with " +
                "\"At the beginning of your upkeep, this emblem deals 1 damage to you.\"";
    }

    private ChandraAwakenedInfernoEffect(final ChandraAwakenedInfernoEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAwakenedInfernoEffect copy() {
        return new ChandraAwakenedInfernoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            game.addEmblem(new ChandraAwakenedInfernoEmblem(), source.getSourceObjectIfItStillExists(game), playerId);
        }
        return true;
    }
}
