package mage.cards.u;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class UlvenwaldMystics extends TransformingDoubleFacedCard {

    public UlvenwaldMystics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF}, "{2}{G}{G}",
                "Ulvenwald Primordials",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Ulvenwald Mystics
        this.getLeftHalfCard().setPT(3, 3);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Ulvenwald Mystics.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Ulvenwald Primordials
        this.getRightHalfCard().setPT(5, 5);

        // {G}: Regenerate Ulvenwald Primordials.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{G}")));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ulvenwald Primordials.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private UlvenwaldMystics(final UlvenwaldMystics card) {
        super(card);
    }

    @Override
    public UlvenwaldMystics copy() {
        return new UlvenwaldMystics(this);
    }
}
