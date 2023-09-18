
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class ImplementOfCombustion extends CardImpl {

    public ImplementOfCombustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {R}, Sacrifice Implement of Combustion: It deals 1 damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, "It"), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

        // When Implement of Combustion is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ImplementOfCombustion(final ImplementOfCombustion card) {
        super(card);
    }

    @Override
    public ImplementOfCombustion copy() {
        return new ImplementOfCombustion(this);
    }
}
