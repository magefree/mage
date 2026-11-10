package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author muz
 */
public final class StarfleetSpannerJockey extends CardImpl {

    public StarfleetSpannerJockey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as you've cast a noncreature spell this turn, this creature can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
            new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
            CastNoncreatureSpellThisTurnCondition.instance
        ).setText("As long as you've cast a noncreature spell this turn, {this} can attack as though it didn't have defender")
        ).addHint(CastNoncreatureSpellThisTurnCondition.getHint()));
    }

    private StarfleetSpannerJockey(final StarfleetSpannerJockey card) {
        super(card);
    }

    @Override
    public StarfleetSpannerJockey copy() {
        return new StarfleetSpannerJockey(this);
    }
}
