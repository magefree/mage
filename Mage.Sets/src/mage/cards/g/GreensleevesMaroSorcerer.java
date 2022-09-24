package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BadgerToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GreensleevesMaroSorcerer extends CardImpl {

    private static final FilterCard filter = new FilterCard("planeswalkers and from Wizards");

    static {
        filter.add(Predicates.or(CardType.PLANESWALKER.getPredicate(), SubType.WIZARD.getPredicate()));
    }

    public GreensleevesMaroSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Protection from planeswalkers and from Wizards
        this.addAbility(new ProtectionAbility(filter));

        // Greensleeves, Maro-Sorcerer's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance, Duration.EndOfGame)
        ));

        // Whenever a land enters the battlefield under your control, create a 3/3 green Badger creature token.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new BadgerToken()),
                StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT, false
        ));
    }

    private GreensleevesMaroSorcerer(final GreensleevesMaroSorcerer card) {
        super(card);
    }

    @Override
    public GreensleevesMaroSorcerer copy() {
        return new GreensleevesMaroSorcerer(this);
    }
}
