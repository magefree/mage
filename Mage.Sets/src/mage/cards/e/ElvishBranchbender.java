
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public final class ElvishBranchbender extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Forest");
    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public ElvishBranchbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Until end of turn, target Forest becomes an X/X Treefolk creature in addition to its other types, where X is the number of Elves you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElvishBranchbenderEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ElvishBranchbender(final ElvishBranchbender card) {
        super(card);
    }

    @Override
    public ElvishBranchbender copy() {
        return new ElvishBranchbender(this);
    }
}

class ElvishBranchbenderEffect extends OneShotEffect {
    
    static final FilterControlledPermanent filter = new FilterControlledPermanent("Elves you control");
    static {
        filter.add(SubType.ELF.getPredicate());
    }
    
    ElvishBranchbenderEffect() {
        super(Outcome.Benefit);
        this.staticText = "Until end of turn, target Forest becomes an X/X Treefolk creature in addition to its other types, where X is the number of Elves you control";
    }
    
    ElvishBranchbenderEffect(final ElvishBranchbenderEffect effect) {
        super(effect);
    }
    
    @Override
    public ElvishBranchbenderEffect copy() {
        return new ElvishBranchbenderEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        ContinuousEffect effect = new BecomesCreatureTargetEffect(
                new ElvishBranchbenderToken(xValue),
                false, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true);
        effect.setTargetPointer(targetPointer);
        game.addEffect(effect, source);
        return false;
    }
}

class ElvishBranchbenderToken extends TokenImpl {

    ElvishBranchbenderToken(int xValue) {
        super("Treefolk", "X/X Treefolk creature in addition to its other types, where X is the number of Elves you control");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TREEFOLK);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }
    public ElvishBranchbenderToken(final ElvishBranchbenderToken token) {
        super(token);
    }

    public ElvishBranchbenderToken copy() {
        return new ElvishBranchbenderToken(this);
    }
}