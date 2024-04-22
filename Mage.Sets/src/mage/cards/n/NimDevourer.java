package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NimDevourer extends CardImpl {

    public NimDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Nim Devourer gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(ArtifactYouControlCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield))
                .addHint(ArtifactYouControlHint.instance)
        );

        // {B}{B}: Return Nim Devourer from your graveyard to the battlefield, then sacrifice a creature. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(false, false),
                new ManaCostsImpl<>("{B}{B}"),
                new IsStepCondition(PhaseStep.UPKEEP), null);
        ability.addEffect(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, ", then"));
        this.addAbility(ability);
    }

    private NimDevourer(final NimDevourer card) {
        super(card);
    }

    @Override
    public NimDevourer copy() {
        return new NimDevourer(this);
    }
}
