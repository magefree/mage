package mage.cards.u;

import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UlvenwaldCaptive extends TransformingDoubleFacedCard {

    public UlvenwaldCaptive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{1}{G}",
                "Ulvenwald Abomination",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, ""
        );

        // Ulvenwald Captive
        this.getLeftHalfCard().setPT(1, 2);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // {T}: Add {G}.
        this.getLeftHalfCard().addAbility(new GreenManaAbility());

        // {5}{G}{G}: Transform Ulvenwald Captive.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}{G}")));

        // Ulvenwald Abomination
        this.getRightHalfCard().setPT(4, 6);

        // {T}: Add {C}{C}.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private UlvenwaldCaptive(final UlvenwaldCaptive card) {
        super(card);
    }

    @Override
    public UlvenwaldCaptive copy() {
        return new UlvenwaldCaptive(this);
    }
}
