package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class Paleoloth extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
        filter.add(AnotherPredicate.instance);
    }

    private static final String rule = "Whenever another creature with power 5 or greater enters the battlefield under your control, you may return target creature card from your graveyard to your hand.";

    public Paleoloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever another creature with power 5 or greater enters the battlefield under your control, you may return target creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), filter, true);
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
