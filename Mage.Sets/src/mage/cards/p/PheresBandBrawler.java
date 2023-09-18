package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PheresBandBrawler extends CardImpl {

    public PheresBandBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Pheres-Band Brawler enters the battlefield, it fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights up to one target creature you don't control. " +
                        "<i>(Each deals damage equal to its power to the other.)</i>"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.addAbility(ability);
    }

    private PheresBandBrawler(final PheresBandBrawler card) {
        super(card);
    }

    @Override
    public PheresBandBrawler copy() {
        return new PheresBandBrawler(this);
    }
}
