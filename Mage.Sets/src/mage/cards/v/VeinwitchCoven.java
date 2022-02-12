package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeinwitchCoven extends CardImpl {

    public VeinwitchCoven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever you gain life, you pay {B}. If you do, return target creature card from your graveyard to your hand.
        Ability ability = new GainLifeControllerTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{B}")
        ), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private VeinwitchCoven(final VeinwitchCoven card) {
        super(card);
    }

    @Override
    public VeinwitchCoven copy() {
        return new VeinwitchCoven(this);
    }
}
