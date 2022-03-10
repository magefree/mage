
package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class RestorationSpecialist extends CardImpl {

    public RestorationSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {W}, Sacrifice Restoration Specialist: Return up to one target artifact card and up to one target enchantment card from your graveyard to your hand.
        Effect effect = new ReturnToHandTargetEffect(true);
        effect.setText("Return up to one target artifact card and up to one target enchantment card from your graveyard to your hand");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{W}"));
        ability.addTarget(new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        ability.addTarget(new TargetCardInGraveyard(0, 1, new FilterEnchantmentCard("enchantment card from your graveyard")));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private RestorationSpecialist(final RestorationSpecialist card) {
        super(card);
    }

    @Override
    public RestorationSpecialist copy() {
        return new RestorationSpecialist(this);
    }
}
