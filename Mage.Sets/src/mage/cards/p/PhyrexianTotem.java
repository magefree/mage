
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfBatchTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class PhyrexianTotem extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition("it's a creature", StaticFilters.FILTER_PERMANENT_CREATURE);

    public PhyrexianTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {2}{B}: Phyrexian Totem becomes a 5/5 black Horror artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new PhyrexianTotemToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}")));

        // Whenever Phyrexian Totem is dealt damage, if it's a creature, sacrifice that many permanents.
        this.addAbility(new ConditionalInterveningIfBatchTriggeredAbility(
                new DealtDamageToSourceTriggeredAbility(new SacrificeControllerEffect(new FilterPermanent(), SavedDamageValue.MANY, "")),
                condition, "Whenever {this} is dealt damage, if it's a creature, sacrifice that many permanents."
        ));
    }

    private PhyrexianTotem(final PhyrexianTotem card) {
        super(card);
    }

    @Override
    public PhyrexianTotem copy() {
        return new PhyrexianTotem(this);
    }

}

class PhyrexianTotemToken extends TokenImpl {
    PhyrexianTotemToken() {
        super("Phyrexian Horror", "5/5 black Phyrexian Horror artifact creature with trample");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(TrampleAbility.getInstance());
    }

    private PhyrexianTotemToken(final PhyrexianTotemToken token) {
        super(token);
    }

    public PhyrexianTotemToken copy() {
        return new PhyrexianTotemToken(this);
    }
}