package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLionTurtle extends CardImpl {

    public TheLionTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When The Lion-Turtle enters, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // The Lion-Turtle can't attack or block unless there are three or more Lesson cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockUnlessConditionSourceEffect(LessonsInGraveCondition.THREE))
                .addHint(LessonsInGraveCondition.getHint()));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private TheLionTurtle(final TheLionTurtle card) {
        super(card);
    }

    @Override
    public TheLionTurtle copy() {
        return new TheLionTurtle(this);
    }
}
