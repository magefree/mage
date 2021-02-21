package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInGraveyardOrBattlefield;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AngelOfSerenity extends CardImpl {

    private static final String rule = "you may exile up to three other target creatures " +
            "from the battlefield and/or creature cards from graveyards.";
    private static final FilterPermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AngelOfSerenity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileTargetForSourceEffect().setText(rule), true
        );
        ability.addTarget(new TargetCardInGraveyardOrBattlefield(
                0, 3, StaticFilters.FILTER_CARD_CREATURE, filter
        ));
        this.addAbility(ability);

        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.HAND, false, true), false));
    }

    private AngelOfSerenity(final AngelOfSerenity card) {
        super(card);
    }

    @Override
    public AngelOfSerenity copy() {
        return new AngelOfSerenity(this);
    }
}
