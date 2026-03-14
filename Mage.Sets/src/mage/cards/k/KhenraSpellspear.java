package mage.cards.k;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KhenraSpellspear extends TransformingDoubleFacedCard {

    public KhenraSpellspear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.JACKAL, SubType.WARRIOR}, "{1}{R}",
                "Gitaxian Spellstalker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.JACKAL}, "UR");

        // Khenra Spellspear
        this.getLeftHalfCard().setPT(2, 2);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Prowess
        this.getLeftHalfCard().addAbility(new ProwessAbility());

        // {3}{U/P}: Transform Khenra Spellspear. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{U/P}")));

        // Gitaxian Spellstalker
        this.getRightHalfCard().setPT(3, 3);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.getRightHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Prowess, Prowess
        this.getRightHalfCard().addAbility(new ProwessAbility());
        this.getRightHalfCard().addAbility(new ProwessAbility());
    }

    private KhenraSpellspear(final KhenraSpellspear card) {
        super(card);
    }

    @Override
    public KhenraSpellspear copy() {
        return new KhenraSpellspear(this);
    }
}
