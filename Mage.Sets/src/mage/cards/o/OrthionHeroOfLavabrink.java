package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrthionHeroOfLavabrink extends CardImpl {

    public OrthionHeroOfLavabrink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{R}, {T}: Create a token that's a copy of another target creature you control. It gains haste. Sacrifice it at the beginning of the next end step. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new OrthionHeroOfLavabrinkEffect(false), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);

        // {6}{R}{R}{R}, {T}: Create five tokens that are copies of another target creature you control. They gain haste. Sacrifice them at the beginning of the next end step. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new OrthionHeroOfLavabrinkEffect(true), new ManaCostsImpl<>("{6}{R}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private OrthionHeroOfLavabrink(final OrthionHeroOfLavabrink card) {
        super(card);
    }

    @Override
    public OrthionHeroOfLavabrink copy() {
        return new OrthionHeroOfLavabrink(this);
    }
}

class OrthionHeroOfLavabrinkEffect extends OneShotEffect {

    private final int amount;

    OrthionHeroOfLavabrinkEffect(boolean five) {
        super(Outcome.Benefit);
        if (five) {
            staticText = "create five tokens that are copies of another target creature you control. " +
                    "They gain haste. Sacrifice them at the beginning of the next end step";
        } else {
            staticText = "create a token that's a copy of another target creature you control. " +
                    "It gains haste. Sacrifice it at the beginning of the next end step";
        }
        this.amount = five ? 5 : 1;
    }

    private OrthionHeroOfLavabrinkEffect(final OrthionHeroOfLavabrinkEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public OrthionHeroOfLavabrinkEffect copy() {
        return new OrthionHeroOfLavabrinkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, amount
        );
        effect.apply(game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game)), source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
