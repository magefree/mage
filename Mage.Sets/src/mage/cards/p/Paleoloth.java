package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Paleoloth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature you control with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
        filter.add(AnotherPredicate.instance);
    }

    public Paleoloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever another creature with power 5 or greater you control enters, you may return target creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), filter, true
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private Paleoloth(final Paleoloth card) {
        super(card);
    }

    @Override
    public Paleoloth copy() {
        return new Paleoloth(this);
    }
}
