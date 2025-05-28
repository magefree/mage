package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdenSeatOfTheSanctum extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("another target permanent card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EdenSeatOfTheSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}: Mill two cards. Then you may sacrifice this land. When you do, return another target permanent card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new MillCardsControllerEffect(2), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ReflexiveTriggeredAbility rAbility = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), false
        );
        rAbility.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addEffect(new DoWhenCostPaid(rAbility, new SacrificeSourceCost(), "Sacrifice this land?").concatBy("Then"));
        this.addAbility(ability);
    }

    private EdenSeatOfTheSanctum(final EdenSeatOfTheSanctum card) {
        super(card);
    }

    @Override
    public EdenSeatOfTheSanctum copy() {
        return new EdenSeatOfTheSanctum(this);
    }
}
