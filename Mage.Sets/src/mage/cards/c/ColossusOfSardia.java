package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ColossusOfSardia extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.UPKEEP);

    public ColossusOfSardia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Colossus of Sardia doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // {9}: Untap Colossus of Sardia. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(new UntapSourceEffect(), new GenericManaCost(9), condition));
    }

    private ColossusOfSardia(final ColossusOfSardia card) {
        super(card);
    }

    @Override
    public ColossusOfSardia copy() {
        return new ColossusOfSardia(this);
    }
}
