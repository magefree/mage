package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RattlebackApothecary extends CardImpl {

    public RattlebackApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you commit a crime, target creature you control gains your choice of menace or lifelink until end of turn.
        Ability ability = new CommittedCrimeTriggeredAbility(new GainsChoiceOfAbilitiesEffect(
                new MenaceAbility(false), LifelinkAbility.getInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private RattlebackApothecary(final RattlebackApothecary card) {
        super(card);
    }

    @Override
    public RattlebackApothecary copy() {
        return new RattlebackApothecary(this);
    }
}
