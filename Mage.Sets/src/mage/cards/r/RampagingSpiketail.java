package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RampagingSpiketail extends CardImpl {

    public RampagingSpiketail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Rampaging Spiketail enters the battlefield, target creature you control gets +2/+0 and gains indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0)
                .setText("target creature you control gets +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and gains indestructible until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private RampagingSpiketail(final RampagingSpiketail card) {
        super(card);
    }

    @Override
    public RampagingSpiketail copy() {
        return new RampagingSpiketail(this);
    }
}
