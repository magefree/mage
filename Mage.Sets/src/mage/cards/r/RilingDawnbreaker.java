package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.Soldier22Token;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class RilingDawnbreaker extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RilingDawnbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{W}",
                "Signaling Roar",
                new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Riling Dawnbreaker
        this.getLeftHalfCard().setPT(3, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // At the beginning of combat on your turn, another target creature you control gets +1/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(1, 0));
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Signaling Roar
        // Create a 2/2 white Soldier creature token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new Soldier22Token()));

        finalizeCard();
    }

    private RilingDawnbreaker(final RilingDawnbreaker card) {
        super(card);
    }

    @Override
    public RilingDawnbreaker copy() {
        return new RilingDawnbreaker(this);
    }
}
