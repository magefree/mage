package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarduMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Mountain, Plains, or Swamp card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.MOUNTAIN.getPredicate(),
                SubType.PLAINS.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    public MarduMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When this artifact enters, search your library for a basic Mountain, Plains, or Swamp card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // {2}{R}{W}{B}, {T}, Sacrifice this artifact: Create three 1/1 red Warrior creature tokens. They gain menace and haste until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new MarduMonumentEffect(), new ManaCostsImpl<>("{2}{R}{W}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MarduMonument(final MarduMonument card) {
        super(card);
    }

    @Override
    public MarduMonument copy() {
        return new MarduMonument(this);
    }
}

class MarduMonumentEffect extends OneShotEffect {

    MarduMonumentEffect() {
        super(Outcome.Benefit);
        staticText = "create three 1/1 red Warrior creature tokens. They gain menace and haste until end of turn";
    }

    private MarduMonumentEffect(final MarduMonumentEffect effect) {
        super(effect);
    }

    @Override
    public MarduMonumentEffect copy() {
        return new MarduMonumentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RedWarriorToken();
        token.putOntoBattlefield(3, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                new MenaceAbility(false), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
