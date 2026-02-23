package mage.cards.k;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KessigProwler extends TransformingDoubleFacedCard {

    public KessigProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{G}",
                "Sinuous Predator",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, "");

        // Kessig Prowler
        this.getLeftHalfCard().setPT(2, 1);

        // {4}{G}: Transform Kessig Prowler.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{G}")));

        // Sinuous Predator
        this.getRightHalfCard().setPT(4, 4);

        // Sinuous Predator can't be blocked by more than one creature.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));
    }

    private KessigProwler(final KessigProwler card) {
        super(card);
    }

    @Override
    public KessigProwler copy() {
        return new KessigProwler(this);
    }
}
