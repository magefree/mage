package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.ArtifactEnteredUnderYourControlCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.ArtifactEnteredControllerWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class FixerTechnoTerror extends CardImpl {

    public FixerTechnoTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}, Pay 2 life: Draw a card. Activate only if an artifact entered under your control this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new DrawCardSourceControllerEffect(1), new TapSourceCost(),
            ArtifactEnteredUnderYourControlCondition.instance
        );
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability, new ArtifactEnteredControllerWatcher());
    }

    private FixerTechnoTerror(final FixerTechnoTerror card) {
        super(card);
    }

    @Override
    public FixerTechnoTerror copy() {
        return new FixerTechnoTerror(this);
    }
}
