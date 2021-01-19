package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IconOfAncestry extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public IconOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Icon of Ancestry enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // {3}, {T}: Look at the top three cards of your library. You may reveal a creature card of the
        // chosen type from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new IconOfAncestryEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private IconOfAncestry(final IconOfAncestry card) {
        super(card);
    }

    @Override
    public IconOfAncestry copy() {
        return new IconOfAncestry(this);
    }
}

class IconOfAncestryEffect extends LookLibraryAndPickControllerEffect {

    public IconOfAncestryEffect() {
        super(StaticValue.get(3), false, StaticValue.get(1), new FilterCreatureCard(
                        "creature card that matches the chosen subtype"
                ), Zone.LIBRARY, false, true, false,
                Zone.HAND, true, false, false);
        this.setOutcome(Outcome.AIDontUseIt);
        this.setBackInRandomOrder(true);
        staticText = "look at the top three cards of your library. " +
                "You may reveal a creature card of the chosen type from among them and put it into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SubType subtype = (SubType) game.getState().getValue(source.getSourceId() + "_type");
        if (subtype == null) {
            return false;
        }
        filter.add(subtype.getPredicate());
        filter.setMessage(subtype.toString() + " creature card");
        return super.apply(game, source);
    }

    public IconOfAncestryEffect(final IconOfAncestryEffect effect) {
        super(effect);
    }

    @Override
    public IconOfAncestryEffect copy() {
        return new IconOfAncestryEffect(this);
    }
}
