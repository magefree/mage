package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.FederationCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class SaavikStoicStudent extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking or blocking creature an opponent controls");

    static {
        filter.add(Predicates.or(
            AttackingPredicate.instance,
            BlockingPredicate.instance
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SaavikStoicStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Federation -- When Saavik enters, she deals X damage to target attacking or blocking creature an opponent controls, where X is the number of creature types among non-Borg creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(FederationCount.instance)
            .setText("{this} deals X damage to target attacking or blocking creature an opponent controls, "
            + "where X is the number of creature types among non-Borg creatures you control"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.FEDERATION).addHint(FederationCount.instance.getHint()));
    }

    private SaavikStoicStudent(final SaavikStoicStudent card) {
        super(card);
    }

    @Override
    public SaavikStoicStudent copy() {
        return new SaavikStoicStudent(this);
    }
}
