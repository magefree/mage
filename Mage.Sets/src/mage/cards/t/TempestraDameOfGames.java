package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TempestraDameOfGames extends CardImpl {

    public TempestraDameOfGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{R}, {T}, Sacrifice an artifact: Create a token that's a copy of another target creature you control, except it isn't legendary.
        // It gains haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new TempestraDameOfGamesEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private TempestraDameOfGames(final TempestraDameOfGames card) {
        super(card);
    }

    @Override
    public TempestraDameOfGames copy() {
        return new TempestraDameOfGames(this);
    }
}

class TempestraDameOfGamesEffect extends OneShotEffect {

    TempestraDameOfGamesEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target creature you control, except it isn't legendary. " +
                "It gains haste. Sacrifice it at the beginning of the next end step";
    }

    private TempestraDameOfGamesEffect(final TempestraDameOfGamesEffect effect) {
        super(effect);
    }

    @Override
    public TempestraDameOfGamesEffect copy() {
        return new TempestraDameOfGamesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setIsntLegendary(true);
        effect.addAdditionalAbilities(HasteAbility.getInstance());
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
