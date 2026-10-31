package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class DGhorBloodletter extends CardImpl {

    public DGhorBloodletter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, choose one --
        // * All other creatures get -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
            -1, -1, Duration.EndOfTurn, true
        ));

        // * Each player sacrifices a creature of their choice.
        ability.addMode(new Mode(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE)));

        this.addAbility(ability);
    }

    private DGhorBloodletter(final DGhorBloodletter card) {
        super(card);
    }

    @Override
    public DGhorBloodletter copy() {
        return new DGhorBloodletter(this);
    }
}
