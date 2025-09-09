package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.AdditionalBeginningPhaseEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author mllagostera
 */
public final class CyclonusCybertronianFighter extends CardImpl {

    public CyclonusCybertronianFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Cyclonus deals combat damage to a player, convert it.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new TransformSourceEffect(),
             false,
              true
            );
        // If you do, there is an additional beginning phase after this phase.
        ability.addEffect(new AdditionalBeginningPhaseEffect(1));

        this.addAbility(ability);
    }

    private CyclonusCybertronianFighter(final CyclonusCybertronianFighter card) {
        super(card);
    }

    @Override
    public CyclonusCybertronianFighter copy() {
        return new CyclonusCybertronianFighter(this);
    }
}
