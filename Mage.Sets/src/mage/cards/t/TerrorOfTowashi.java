package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class TerrorOfTowashi extends CardImpl {

    public TerrorOfTowashi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Terror of Towashi attacks, you may pay {3}{B}. When you do, return target creature card from your graveyard to the battlefield. It's a Phyrexian in addition to its other types.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addEffect(new AddCreatureTypeAdditionEffect(SubType.PHYREXIAN, false));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{3}{B}"), "Pay {3}{B}?"
        )));
    }

    private TerrorOfTowashi(final TerrorOfTowashi card) {
        super(card);
    }

    @Override
    public TerrorOfTowashi copy() {
        return new TerrorOfTowashi(this);
    }
}
