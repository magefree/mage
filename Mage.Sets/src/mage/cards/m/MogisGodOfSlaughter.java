package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MogisGodOfSlaughter extends CardImpl {

    public MogisGodOfSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to black and red is less than seven, Mogis isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.BR, 7))
                .addHint(DevotionCount.BR.getHint()));

        // At the beginning of each opponent's upkeep, Mogis deals 2 damage to that player unless they sacrifice a creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(TargetController.OPPONENT, new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                new DamageTargetEffect(2).withTargetDescription("that player"),new ManaCostsImpl<>("{2}")).withTheyText(),
                false
        );
        this.addAbility(ability);
    }

    private MogisGodOfSlaughter(final MogisGodOfSlaughter card) {
        super(card);
    }

    @Override
    public MogisGodOfSlaughter copy() {
        return new MogisGodOfSlaughter(this);
    }
}
