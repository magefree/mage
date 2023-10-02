package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class AngelOfCondemnation extends CardImpl {

    public AngelOfCondemnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}{W}, {T}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(
                new ExileReturnBattlefieldNextEndStepTargetEffect(), new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // {2}{W}, {T}, Exert Angel of Condemnation: Exile another target creature until Angel of Condemnation leaves the battlefield.
        ability = new SimpleActivatedAbility(new ExileUntilSourceLeavesEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private AngelOfCondemnation(final AngelOfCondemnation card) {
        super(card);
    }

    @Override
    public AngelOfCondemnation copy() {
        return new AngelOfCondemnation(this);
    }
}
