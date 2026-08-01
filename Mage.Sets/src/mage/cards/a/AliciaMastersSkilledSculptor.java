package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlAllOwnedEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AliciaMastersSkilledSculptor extends CardImpl {

    public AliciaMastersSkilledSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, create a Treasure token.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
            new CreateTokenEffect(new TreasureToken()
        )).withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance));

        // Sense the Good -- At the beginning of your end step, each player gains control of all creatures they own.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new GainControlAllOwnedEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
        ).withFlavorWord("Sense the Good"));
    }

    private AliciaMastersSkilledSculptor(final AliciaMastersSkilledSculptor card) {
        super(card);
    }

    @Override
    public AliciaMastersSkilledSculptor copy() {
        return new AliciaMastersSkilledSculptor(this);
    }
}
