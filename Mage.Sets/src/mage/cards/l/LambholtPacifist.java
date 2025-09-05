package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LambholtPacifist extends CardImpl {

    private static final Condition condition = new InvertCondition(FerociousCondition.instance);

    public LambholtPacifist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.l.LambholtButcher.class;

        // Lambholt Pacifist can't attack unless you control a creature with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackSourceEffect(Duration.WhileOnBattlefield), condition,
                "{this} can't attack unless you control a creature with power 4 or greater"
        )).addHint(FerociousHint.instance));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Pacifist.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private LambholtPacifist(final LambholtPacifist card) {
        super(card);
    }

    @Override
    public LambholtPacifist copy() {
        return new LambholtPacifist(this);
    }
}
