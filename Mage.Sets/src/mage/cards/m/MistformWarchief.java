
package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistformWarchief extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new MistformWarchiefPredicate());
    }

    public MistformWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Creature spells you cast that share a creature type with Mistform Warchief cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 1)
                        .setText("Creature spells you cast that share a creature type with {this} cost {1} less to cast")
        ));

        // {tap}: Mistform Warchief becomes the creature type of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesChosenCreatureTypeSourceEffect(), new TapSourceCost()));
    }

    private MistformWarchief(final MistformWarchief card) {
        super(card);
    }

    @Override
    public MistformWarchief copy() {
        return new MistformWarchief(this);
    }
}

class MistformWarchiefPredicate implements ObjectSourcePlayerPredicate<Card> {

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        MageObject sourceObject = input.getSource().getSourceObject(game);
        return sourceObject != null && sourceObject.shareCreatureTypes(game, input.getObject());
    }

    @Override
    public String toString() {
        return "shares a creature type";
    }
}
