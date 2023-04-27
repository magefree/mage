package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ProliferatedControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.TheHollowSentinelToken;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class VenserCorpsePuppet extends CardImpl {

    private static final FilterPermanent filter =
            new FilterControlledCreaturePermanent("you don't control a creature named The Hollow Sentinel");
    private static final FilterPermanent filter2 =
            new FilterControlledCreaturePermanent("artifact creature you control");

    static {
        filter.add(new NamePredicate("The Hollow Sentinel"));
        filter2.add(CardType.ARTIFACT.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public VenserCorpsePuppet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Whenever you proliferate, choose one --
        // * If you don't control a creature named The Hollow Sentinel, create The Hollow Sentinel, a legendary 3/3 colorless Phyrexian Golem artifact creature token.
        Ability ability = new ProliferatedControllerTriggeredAbility(
                new ConditionalOneShotEffect(new CreateTokenEffect(new TheHollowSentinelToken()), condition)
        );

        // * Target artifact creature you control gains flying and lifelink until end of turn.
        ability.addMode(new Mode(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("target artifact creature you control gains flying"))
                .addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                        .setText("and lifelink until end of turn"))
                .addTarget(new TargetPermanent(filter2)));
        this.addAbility(ability);
    }

    private VenserCorpsePuppet(final VenserCorpsePuppet card) {
        super(card);
    }

    @Override
    public VenserCorpsePuppet copy() {
        return new VenserCorpsePuppet(this);
    }
}
