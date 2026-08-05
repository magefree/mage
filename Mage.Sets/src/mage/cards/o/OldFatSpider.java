package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class OldFatSpider extends CardImpl {

    public OldFatSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // This creature can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Whenever this creature becomes the target of a spell or ability an opponent controls, draw a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
            new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS
        ));
    }

    private OldFatSpider(final OldFatSpider card) {
        super(card);
    }

    @Override
    public OldFatSpider copy() {
        return new OldFatSpider(this);
    }
}
