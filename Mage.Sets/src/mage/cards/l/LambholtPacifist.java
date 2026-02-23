package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LambholtPacifist extends TransformingDoubleFacedCard {

    private static final Condition condition = new InvertCondition(FerociousCondition.instance);

    public LambholtPacifist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF}, "{1}{G}",
                "Lambholt Butcher",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(4, 4);

        // Lambholt Pacifist can't attack unless you control a creature with power 4 or greater.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackSourceEffect(Duration.WhileOnBattlefield), condition,
                "{this} can't attack unless you control a creature with power 4 or greater"
        )).addHint(FerociousHint.instance));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Pacifist.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Lambholt Butcher

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Lambholt Butcher.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private LambholtPacifist(final LambholtPacifist card) {
        super(card);
    }

    @Override
    public LambholtPacifist copy() {
        return new LambholtPacifist(this);
    }
}
