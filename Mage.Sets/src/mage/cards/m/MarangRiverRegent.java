package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class MarangRiverRegent extends OmenCard {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanents");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MarangRiverRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{U}{U}",
                "Coil and Catch",
                new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Marang River Regent
        this.getLeftHalfCard().setPT(6, 7);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature enters, return up to two other target nonland permanents to their owners' hands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.getLeftHalfCard().addAbility(ability);

        // Coil and Catch
        // Draw three cards, then discard a card.
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 1));

        finalizeCard();
    }

    private MarangRiverRegent(final MarangRiverRegent card) {
        super(card);
    }

    @Override
    public MarangRiverRegent copy() {
        return new MarangRiverRegent(this);
    }
}
