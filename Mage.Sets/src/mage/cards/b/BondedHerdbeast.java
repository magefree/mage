package mage.cards.b;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondedHerdbeast extends TransformingDoubleFacedCard {

    public BondedHerdbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BEAST}, "{4}{G}",
                "Plated Kilnbeast",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.BEAST}, "RG");

        // Bonded Herdbeast
        this.getLeftHalfCard().setPT(4, 5);

        // {4}{R/P}: Transform Bonded Herdbeast. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{R/P}")));

        // Plated Kilnbeast
        this.getRightHalfCard().setPT(7, 5);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());
    }

    private BondedHerdbeast(final BondedHerdbeast card) {
        super(card);
    }

    @Override
    public BondedHerdbeast copy() {
        return new BondedHerdbeast(this);
    }
}
