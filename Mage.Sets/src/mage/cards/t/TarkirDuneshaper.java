package mage.cards.t;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TarkirDuneshaper extends TransformingDoubleFacedCard {

    public TarkirDuneshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DOG, SubType.WARRIOR}, "{W}",
                "Burnished Dunestomper",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.DOG, SubType.WARRIOR}, "WG"
        );

        // Tarkir Duneshaper
        this.getLeftHalfCard().setPT(1, 2);

        // {4}{G/P}: Transform Tarkir Duneshaper. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{G/P}")));

        // Burnished Dunestomper
        this.getRightHalfCard().setPT(4, 3);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());
    }

    private TarkirDuneshaper(final TarkirDuneshaper card) {
        super(card);
    }

    @Override
    public TarkirDuneshaper copy() {
        return new TarkirDuneshaper(this);
    }
}
