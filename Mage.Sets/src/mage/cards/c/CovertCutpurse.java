package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovertCutpurse extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control that was dealt damage this turn");
    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public CovertCutpurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "{2}{B}",
                "Covetous Geist",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.ROGUE}, "B");

        // Covert Cutpurse
        this.getLeftHalfCard().setPT(2, 1);

        // When Covert Cutpurse enters the battlefield, destroy target creature you don't control that was dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Disturb {4}{B}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{4}{B}"));

        // Covetous Geist
        this.getRightHalfCard().setPT(2, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // If Covetous Geist would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private CovertCutpurse(final CovertCutpurse card) { super(card); }
    @Override public CovertCutpurse copy() { return new CovertCutpurse(this); }
}
