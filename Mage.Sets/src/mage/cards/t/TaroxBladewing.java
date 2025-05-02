package mage.cards.t;

import mage.MageInt;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class TaroxBladewing extends CardImpl {

    public TaroxBladewing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Grandeur - Discard another card named Tarox Bladewing: Tarox Bladewing gets +X/+X until end of turn, where X is its power.
        this.addAbility(new GrandeurAbility(new BoostSourceEffect(SourcePermanentPowerValue.NOT_NEGATIVE, SourcePermanentPowerValue.NOT_NEGATIVE, Duration.EndOfTurn), "Tarox Bladewing"));
    }

    private TaroxBladewing(final TaroxBladewing card) {
        super(card);
    }

    @Override
    public TaroxBladewing copy() {
        return new TaroxBladewing(this);
    }
}
