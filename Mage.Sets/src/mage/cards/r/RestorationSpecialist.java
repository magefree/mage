package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class RestorationSpecialist extends CardImpl {

    public RestorationSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {W}, Sacrifice Restoration Specialist: Return up to one target artifact card and up to one target enchantment card from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect().setTargetPointer(new EachTargetPointer());
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_ARTIFACT));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, new FilterEnchantmentCard()));
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
