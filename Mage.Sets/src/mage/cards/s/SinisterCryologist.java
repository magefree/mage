package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SinisterCryologist extends CardImpl {

    public SinisterCryologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, target creature an opponent controls gets -3/-0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // Warp {U}
        this.addAbility(new WarpAbility(this, "{U}"));
    }

    private SinisterCryologist(final SinisterCryologist card) {
        super(card);
    }

    @Override
    public SinisterCryologist copy() {
        return new SinisterCryologist(this);
    }
}
