package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LabyrinthOfSkophos extends CardImpl {

    public LabyrinthOfSkophos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Remove target attacking or blocking creature from combat.
        Ability ability = new SimpleActivatedAbility(
                new RemoveFromCombatTargetEffect(), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    private LabyrinthOfSkophos(final LabyrinthOfSkophos card) {
        super(card);
    }

    @Override
    public LabyrinthOfSkophos copy() {
        return new LabyrinthOfSkophos(this);
    }
}
