
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author EvilGeek
 */
public final class UnnaturalSelection extends CardImpl {

    public UnnaturalSelection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // {1}: Choose a creature type other than Wall. Target creature becomes that type until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesChosenCreatureTypeTargetEffect(true), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private UnnaturalSelection(final UnnaturalSelection card) {
        super(card);
    }

    @Override
    public UnnaturalSelection copy() {
        return new UnnaturalSelection(this);
    }
}
