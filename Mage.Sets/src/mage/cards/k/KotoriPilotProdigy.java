package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KotoriPilotProdigy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicles");
    private static final FilterPermanent filter2 = new FilterArtifactCreaturePermanent("artifact creature you control");

    static {
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public KotoriPilotProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vehicles you control have crew 2.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new CrewAbility(2), Duration.WhileOnBattlefield, filter
        )));

        // At the beginning of combat on your turn, target artifact creature you control gains lifelink and vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                        .setText("target artifact creature you control gains lifelink"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance()
        ).setText("and vigilance until end of turn"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private KotoriPilotProdigy(final KotoriPilotProdigy card) {
        super(card);
    }

    @Override
    public KotoriPilotProdigy copy() {
        return new KotoriPilotProdigy(this);
    }
}
