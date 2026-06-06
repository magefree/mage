package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class TwinmawStormbrood extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TwinmawStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{5}{W}",
                "Charring Bite",
                new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Twinmaw Stormbrood
        this.getLeftHalfCard().setPT(5, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature enters, you gain 5 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5)));

        // Charring Bite
        // deals 5 damage to target creature without flying.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private TwinmawStormbrood(final TwinmawStormbrood card) {
        super(card);
    }

    @Override
    public TwinmawStormbrood copy() {
        return new TwinmawStormbrood(this);
    }
}
