package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnwelcomeSprite extends CardImpl {

    public UnwelcomeSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell during an opponent's turn, surveil 2.
        this.addAbility(new SpellCastControllerTriggeredAbility(new SurveilEffect(2), false)
                .withTriggerCondition(OpponentsTurnCondition.instance));
    }

    private UnwelcomeSprite(final UnwelcomeSprite card) {
        super(card);
    }

    @Override
    public UnwelcomeSprite copy() {
        return new UnwelcomeSprite(this);
    }
}
