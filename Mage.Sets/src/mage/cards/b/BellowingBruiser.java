package mage.cards.b;

import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BellowingBruiser extends AdventureCard {

    public BellowingBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.OGRE}, "{4}{R}",
                "Beat a Path",
                new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Bellowing Bruiser
        this.getLeftHalfCard().setPT(4, 4);

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // Beat a Path
        // Up to two target creatures can't block this turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        finalizeCard();
    }

    private BellowingBruiser(final BellowingBruiser card) {
        super(card);
    }

    @Override
    public BellowingBruiser copy() {
        return new BellowingBruiser(this);
    }
}
