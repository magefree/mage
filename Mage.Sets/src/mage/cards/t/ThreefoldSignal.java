package mage.cards.t;

import mage.MageObject;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.EachSpellYouCastHasReplicateEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCardHalf;
import mage.constants.CardType;
import mage.constants.Zone;
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

        // Each spell you cast that’s exactly three colors has replicate {3}.
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
        // 708.3. Each split card that consists of two halves with different colored mana symbols in their mana costs
        // is a multicolored card while it's not a spell on the stack. While it's a spell on the stack, it's only the
        // color or colors of the half or halves being cast. #
        if (input instanceof SplitCardHalf
                && game.getState().getZone(input.getId()) != Zone.STACK) {
            return 3 == ((SplitCardHalf) input).getMainCard().getColor(game).getColorCount();
        } else if (input instanceof ModalDoubleFacesCardHalf
                && (game.getState().getZone(input.getId()) != Zone.STACK && game.getState().getZone(input.getId()) != Zone.BATTLEFIELD)) {
            // While a double-faced card isn’t on the stack or battlefield, consider only the characteristics of its front face.
            return 3 == ((ModalDoubleFacesCardHalf) input).getMainCard().getColor(game).getColorCount();
        } else {
            return 3 == input.getColor(game).getColorCount();
        }
    }

    @Override
    public String toString() {
        return "Multicolored";
    }
}