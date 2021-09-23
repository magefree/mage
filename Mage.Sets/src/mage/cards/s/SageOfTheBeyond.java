package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SageOfTheBeyond extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SageOfTheBeyondPredicate.instance);
    }

    public SageOfTheBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells you cast from anywhere other than your hand cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)
                .setText("Spells you cast from anywhere other than your hand cost {2} less to cast.")));

        // Foretell {4}{U}
        this.addAbility(new ForetellAbility(this, "{4}{U}"));
    }

    private SageOfTheBeyond(final SageOfTheBeyond card) {
        super(card);
    }

    @Override
    public SageOfTheBeyond copy() {
        return new SageOfTheBeyond(this);
    }
}

enum SageOfTheBeyondPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() instanceof Spell) {
            return !input.getObject().isOwnedBy(input.getPlayerId())
                    || !Zone.HAND.match(((Spell) input.getObject()).getFromZone());
        } else {
            return !input.getObject().isOwnedBy(input.getPlayerId())
                    || !Zone.HAND.match(game.getState().getZone(input.getObject().getId()));
        }
    }
}
