package mage.cards.t;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThrabenGargoyle extends TransformingDoubleFacedCard {

    public ThrabenGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GARGOYLE}, "{1}",
                "Stonewing Antagonizer",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GARGOYLE, SubType.HORROR}, ""
        );

        // Thraben Gargoyle
        this.getLeftHalfCard().setPT(2, 2);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // {6}: Transform Thraben Gargoyle.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(6)));

        // Stonewing Antagonizer
        this.getRightHalfCard().setPT(4, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private ThrabenGargoyle(final ThrabenGargoyle card) {
        super(card);
    }

    @Override
    public ThrabenGargoyle copy() {
        return new ThrabenGargoyle(this);
    }
}
