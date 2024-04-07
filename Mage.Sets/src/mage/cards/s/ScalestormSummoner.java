package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Dinosaur31Token;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ScalestormSummoner extends CardImpl {

    public ScalestormSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Scalestorm Summoner attacks, create a 3/1 red Dinosaur creature token if you control a creature with power 4 or greater.
        this.addAbility(new AttacksTriggeredAbility(
                new ConditionalOneShotEffect(
                        new CreateTokenEffect(new Dinosaur31Token()),
                        FerociousCondition.instance,
                        "create a 3/1 red Dinosaur creature token "
                                + "if you control a creature with power 4 or greater"
                )
        ).addHint(FerociousHint.instance));
    }

    private ScalestormSummoner(final ScalestormSummoner card) {
        super(card);
    }

    @Override
    public ScalestormSummoner copy() {
        return new ScalestormSummoner(this);
    }
}
