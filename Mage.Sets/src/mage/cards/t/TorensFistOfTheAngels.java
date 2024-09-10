package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanSoldierTrainingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TorensFistOfTheAngels extends CardImpl {

    public TorensFistOfTheAngels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Training
        this.addAbility(new TrainingAbility());

        // Whenever you cast a creature spell, create a 1/1 green and white Human Soldier creature token with training.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierTrainingToken()),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));
    }

    private TorensFistOfTheAngels(final TorensFistOfTheAngels card) {
        super(card);
    }

    @Override
    public TorensFistOfTheAngels copy() {
        return new TorensFistOfTheAngels(this);
    }
}
