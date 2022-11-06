package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class AdarkarValkyrie extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AdarkarValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {T}: When target creature other than Adarkar Valkyrie dies this turn, return that card to the battlefield under your control.
        DelayedTriggeredAbility delayedAbility = new WhenTargetDiesDelayedTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("return that card to the battlefield under your control"),
                SetTargetPointer.CARD
        );
        delayedAbility.setTriggerPhrase("When target creature other than {this} dies this turn, ");
        Ability ability = new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(delayedAbility), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AdarkarValkyrie(final AdarkarValkyrie card) {
        super(card);
    }

    @Override
    public AdarkarValkyrie copy() {
        return new AdarkarValkyrie(this);
    }
}
