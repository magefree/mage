package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UlrichOfTheKrallenhorde extends CardImpl {

    public UlrichOfTheKrallenhorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = UlrichUncontestedAlpha.class;

        // Whenever this creature enters the battlefield or transforms into Ulrich of the Krallenhorde, target creature gets +4/+4 until end of turn.
        Ability ability = new TransformsOrEntersTriggeredAbility(
                new BoostTargetEffect(4, 4), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Ulrich of the Krallenhorde.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private UlrichOfTheKrallenhorde(final UlrichOfTheKrallenhorde card) {
        super(card);
    }

    @Override
    public UlrichOfTheKrallenhorde copy() {
        return new UlrichOfTheKrallenhorde(this);
    }
}
