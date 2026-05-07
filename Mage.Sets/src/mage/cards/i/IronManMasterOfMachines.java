package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.watchers.common.ArtifactEnteredControllerWatcher;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ArtifactEnteredUnderYourControlCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IronManMasterOfMachines extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("other artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public IronManMasterOfMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Iron Man gets +1/+0 for each other artifact you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
            xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // Whenever Iron Man attacks, if an artifact entered the battlefield under your control this turn, draw a card.
        this.addAbility(
            new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(ArtifactEnteredUnderYourControlCondition.instance)
                .addHint(new ConditionHint(ArtifactEnteredUnderYourControlCondition.instance)),
            new ArtifactEnteredControllerWatcher()
        );
    }

    private IronManMasterOfMachines(final IronManMasterOfMachines card) {
        super(card);
    }

    @Override
    public IronManMasterOfMachines copy() {
        return new IronManMasterOfMachines(this);
    }
}
