package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class DirgurIslandDragon extends OmenCard {

    public DirgurIslandDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{5}{U}",
                "Skimming Strike",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Dirgur, Island Dragon
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Skimming Strike
        // Tap up to one target creature. Draw a card.
        this.getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));

        finalizeCard();
    }

    private DirgurIslandDragon(final DirgurIslandDragon card) {
        super(card);
    }

    @Override
    public DirgurIslandDragon copy() {
        return new DirgurIslandDragon(this);
    }
}
