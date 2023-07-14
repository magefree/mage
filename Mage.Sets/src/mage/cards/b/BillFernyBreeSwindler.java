package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author bobby-mccann
 */
public final class BillFernyBreeSwindler extends CardImpl {

    public BillFernyBreeSwindler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Bill Ferny, Bree Swindler becomes blocked, choose one --
        Ability ability = new BecomesBlockedSourceTriggeredAbility(
                // * Create a Treasure token.
                new CreateTokenEffect(new TreasureToken()), false
        );
        ability.addMode(
                // * Target opponent gains control of target Horse you control. If they do, remove Bill Ferny from combat and create three Treasure tokens.
                new Mode(new BillFernyEffect())
                        .addTarget(new TargetOpponent())
                        .addTarget(new TargetControlledPermanent(
                                new FilterControlledPermanent(SubType.HORSE)
                        ))
        );
        this.addAbility(ability);
    }

    private BillFernyBreeSwindler(final BillFernyBreeSwindler card) {
        super(card);
    }

    @Override
    public BillFernyBreeSwindler copy() {
        return new BillFernyBreeSwindler(this);
    }
}

class BillFernyEffect extends OneShotEffect {

    private static final Effect create3TreasureTokens = new CreateTokenEffect(new TreasureToken(), 3);
    private static final Effect removeFromCombat = new RemoveFromCombatSourceEffect();

    public BillFernyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent gains control of target Horse you control. If they do, remove Bill Ferny from combat and create three Treasure tokens.";
    }

    private BillFernyEffect(BillFernyEffect effect) {
        super(effect);
    }

    @Override
    public BillFernyEffect copy() {
        return new BillFernyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent == null) {
            return false;
        }
        UUID opponentToGainControl = targetPointer.getFirst(game, source);
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, opponentToGainControl
        ).setTargetPointer(new FixedTarget(permanent.getId(), game)), source);
        game.getState().processAction(game);
        if (permanent.isControlledBy(opponentToGainControl)) {
            removeFromCombat.apply(game, source);
            create3TreasureTokens.apply(game, source);
            return true;
        }
        return false;
    }
}
