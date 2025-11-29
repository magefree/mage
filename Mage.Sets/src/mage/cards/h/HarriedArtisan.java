package mage.cards.h;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarriedArtisan extends TransformingDoubleFacedCard {

    public HarriedArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ARTIFICER}, "{2}{R}",
                "Phyrexian Skyflayer",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.ARTIFICER}, "WR"
        );

        // Harried Artisan
        this.getLeftHalfCard().setPT(2, 3);

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // {3}{W/P}: Transform Harried Artisan. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{W/P}")));

        // Phyrexian Skyflayer
        this.getRightHalfCard().setPT(3, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());
    }

    private HarriedArtisan(final HarriedArtisan card) {
        super(card);
    }

    @Override
    public HarriedArtisan copy() {
        return new HarriedArtisan(this);
    }
}
