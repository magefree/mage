package mage.cards.t;

import mage.MageObject;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.EachSpellYouCastHasReplicateEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ThreefoldSignal extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you cast that's exactly three colors");
    static {
        filter.add(ThreeColorPredicate.instance);
    }

    public ThreefoldSignal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When Threefold Signal enters the battlefield, scry 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(3)));

        // Each spell you cast that's exactly three colors has replicate {3}.
        //  (When you cast it, copy it for each time you paid its replicate cost.
        //   You may choose new targets for the copies.
        //   A copy of a permanent spell becomes a token.)
        this.addAbility(new SimpleStaticAbility(new EachSpellYouCastHasReplicateEffect(filter, new GenericManaCost(3))));
    }

    private ThreefoldSignal(final ThreefoldSignal card) {
        super(card);
    }

    @Override
    public ThreefoldSignal copy() {
        return new ThreefoldSignal(this);
    }
}

/**
 * Based on MultiColorPredicate
 */
enum ThreeColorPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return 3 == input.getColor(game).getColorCount();
    }

    @Override
    public String toString() {
        return "Multicolored";
    }
}
