
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author TheElk801
 */
public final class WeatherseedTotem extends CardImpl {

    public WeatherseedTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}{G}{G}: Weatherseed Totem becomes a 5/3 green Treefolk artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new WeatherseedTotemToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}{G}{G}")));

        // When Weatherseed Totem is put into a graveyard from the battlefield, if it was a creature, return this card to its owner's hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()),
                new WeatherseedTotemCondition(),
                "When {this} is put into a graveyard from the battlefield, "
                + "if it was a creature, return this card to its owner's hand"
        ));
    }

    private WeatherseedTotem(final WeatherseedTotem card) {
        super(card);
    }

    @Override
    public WeatherseedTotem copy() {
        return new WeatherseedTotem(this);
    }
}

class WeatherseedTotemCondition implements Condition {

    public WeatherseedTotemCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            return permanent.isCreature(game);
        }
        return false;
    }
}

class WeatherseedTotemToken extends TokenImpl {

    public WeatherseedTotemToken() {
        super("", "5/3 green Treefolk artifact creature with trample");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TREEFOLK);
        color.setGreen(true);
        power = new MageInt(5);
        toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
    }
    public WeatherseedTotemToken(final WeatherseedTotemToken token) {
        super(token);
    }

    public WeatherseedTotemToken copy() {
        return new WeatherseedTotemToken(this);
    }
}
