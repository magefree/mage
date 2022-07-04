package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
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
public final class EternalTaskmaster extends CardImpl {

    public EternalTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Eternal Taskmaster enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever Eternal Taskmaster attacks, you may pay {2}{B}. If you do, return target creature card from your graveyard to your hand.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(
                new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{2}{B}")
        ), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private EternalTaskmaster(final EternalTaskmaster card) {
        super(card);
    }

    @Override
    public EternalTaskmaster copy() {
        return new EternalTaskmaster(this);
    }
}
