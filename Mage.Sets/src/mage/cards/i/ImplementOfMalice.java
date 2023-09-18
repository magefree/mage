
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class ImplementOfMalice extends CardImpl {

    public ImplementOfMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {B}, Sacrifice Implement of Malice: Target player discards a card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1),new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Implement of Malice is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ImplementOfMalice(final ImplementOfMalice card) {
        super(card);
    }

    @Override
    public ImplementOfMalice copy() {
        return new ImplementOfMalice(this);
    }
}
