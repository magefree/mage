package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class ViperCruelConspirator extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that's attacking alone");

    static {
        filter.add(AttackingAlonePredicate.instance);
    }

    public ViperCruelConspirator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}: Target creature that's attacking alone gets +1/+1 until end of turn.
        Ability boostAbility = new SimpleActivatedAbility(
            new BoostTargetEffect(1, 1, Duration.EndOfTurn),
            new ManaCostsImpl<>("{B}")
        );
        boostAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(boostAbility);

        // {B}: Target creature that's attacking alone gains your choice of deathtouch or lifelink until end of turn.
        Ability gainAbility = new SimpleActivatedAbility(
            new GainsChoiceOfAbilitiesEffect(
                GainsChoiceOfAbilitiesEffect.TargetType.Target, "", true,
                DeathtouchAbility.getInstance(), LifelinkAbility.getInstance()
            ),
            new ManaCostsImpl<>("{B}")
        );
        gainAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(gainAbility);
    }

    private ViperCruelConspirator(final ViperCruelConspirator card) {
        super(card);
    }

    @Override
    public ViperCruelConspirator copy() {
        return new ViperCruelConspirator(this);
    }
}

enum AttackingAlonePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isAttacking()
            && game.getCombat().attacksAlone()
            && game.getCombat().getAttackers().contains(input.getId());
    }

    @Override
    public String toString() {
        return "Attacking alone";
    }
}
