package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToYouTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MichikoKondaTruthSeeker extends CardImpl {

    public MichikoKondaTruthSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a source an opponent controls deals damage to you, that player sacrifices a permanent.
        this.addAbility(new SourceDealsDamageToYouTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_A, 1, "that player"), false));
    }

    private MichikoKondaTruthSeeker(final MichikoKondaTruthSeeker card) {
        super(card);
    }

    @Override
    public MichikoKondaTruthSeeker copy() {
        return new MichikoKondaTruthSeeker(this);
    }
}
