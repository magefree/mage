package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrauliosOfPheresBand extends CardImpl {

    public BrauliosOfPheresBand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Braulios of Pheres Band's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance)));

        // Whenever Braulios of Pheres Band attacks, draw a card, then you may put a land card from your hand onto the battlefield.
        Ability ability = new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A).concatBy(", then"));
        this.addAbility(ability);
    }

    private BrauliosOfPheresBand(final BrauliosOfPheresBand card) {
        super(card);
    }

    @Override
    public BrauliosOfPheresBand copy() {
        return new BrauliosOfPheresBand(this);
    }
}
