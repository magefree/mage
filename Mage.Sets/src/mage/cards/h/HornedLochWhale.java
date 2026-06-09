package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornedLochWhale extends AdventureCard {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public HornedLochWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WHALE}, "{4}{U}{U}",
                "Lagoon Breach",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Horned Loch-Whale
        this.getLeftHalfCard().setPT(6, 6);

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // Ward {2}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Horned Loch-Whale enters the battlefield tapped unless it's your turn.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedUnlessAbility(MyTurnCondition.instance, "it's your turn"));

        // Lagoon Breach
        // The owner of target attacking creature you don't control puts it on the top or bottom of their library.
        this.getRightHalfCard().getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(true));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private HornedLochWhale(final HornedLochWhale card) {
        super(card);
    }

    @Override
    public HornedLochWhale copy() {
        return new HornedLochWhale(this);
    }
}
