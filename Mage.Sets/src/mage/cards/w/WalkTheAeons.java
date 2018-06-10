
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class WalkTheAeons extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("three Islands");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    public WalkTheAeons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");


        // Buyback—Sacrifice three Islands. (You may sacrifice three Islands in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.)
        this.addAbility(new BuybackAbility(new SacrificeTargetCost(new TargetControlledPermanent(3,3, filter, true))));

        // Target player takes an extra turn after this one.
        this.getSpellAbility().addEffect(new ExtraTurnEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public WalkTheAeons(final WalkTheAeons card) {
        super(card);
    }

    @Override
    public WalkTheAeons copy() {
        return new WalkTheAeons(this);
    }
}

class ExtraTurnEffect extends OneShotEffect {

    public ExtraTurnEffect() {
        super(Outcome.ExtraTurn);
        staticText = "Target player takes an extra turn after this one";
    }

    public ExtraTurnEffect(final ExtraTurnEffect effect) {
        super(effect);
    }

    @Override
    public ExtraTurnEffect copy() {
        return new ExtraTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(getTargetPointer().getFirst(game, source), false));
        return true;
    }

}