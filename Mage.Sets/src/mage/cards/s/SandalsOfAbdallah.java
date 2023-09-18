package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class SandalsOfAbdallah extends CardImpl {

    public SandalsOfAbdallah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}: Target creature gains islandwalk until end of turn. When that creature dies this turn, destroy Sandals of Abdallah.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(new IslandwalkAbility()), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new DestroySourceEffect())
        ));
        this.addAbility(ability);
    }

    private SandalsOfAbdallah(final SandalsOfAbdallah card) {
        super(card);
    }

    @Override
    public SandalsOfAbdallah copy() {
        return new SandalsOfAbdallah(this);
    }
}
