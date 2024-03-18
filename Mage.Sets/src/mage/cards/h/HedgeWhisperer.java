package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class HedgeWhisperer extends CardImpl {

    public HedgeWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // You may choose not to untap Hedge Whisperer during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {3}{G}, {T}, Collect evidence 4: Target land you control becomes a 5/5 green Plant Boar creature with haste
        // for as long as Hedge Whisperer remains tapped. It's still a land. Activate only as a sorcery.
        BecomesCreatureTargetEffect effect = new BecomesCreatureTargetEffect(
                new CreatureToken(5, 5, "5/5 green Plant Boar creature with haste")
                        .withColor("G")
                        .withSubType(SubType.PLANT)
                        .withSubType(SubType.BOAR)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom);

        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ConditionalContinuousEffect(
                        effect,
                        SourceTappedCondition.TAPPED,
                        "target land you control becomes a 5/5 green Plant Boar creature with haste for as long as {this} remains tapped. It's still a land"),
                new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new CollectEvidenceCost(4));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private HedgeWhisperer(final HedgeWhisperer card) {
        super(card);
    }

    @Override
    public HedgeWhisperer copy() {
        return new HedgeWhisperer(this);
    }
}
