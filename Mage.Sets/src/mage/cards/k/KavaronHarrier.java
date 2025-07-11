package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.RobotToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KavaronHarrier extends CardImpl {

    public KavaronHarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever this creature attacks, you may pay {2}. If you do, create a 2/2 colorless Robot artifact creature token that's tapped and attacking. Sacrifice that token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(new KavaronHarrierEffect(), new GenericManaCost(2))
        ));
    }

    private KavaronHarrier(final KavaronHarrier card) {
        super(card);
    }

    @Override
    public KavaronHarrier copy() {
        return new KavaronHarrier(this);
    }
}


class KavaronHarrierEffect extends OneShotEffect {

    KavaronHarrierEffect() {
        super(Outcome.Benefit);
        staticText = "create a 2/2 colorless Robot artifact creature token that's tapped and attacking. "
                + "Sacrifice that token at end of combat";
    }

    private KavaronHarrierEffect(final KavaronHarrierEffect effect) {
        super(effect);
    }

    @Override
    public KavaronHarrierEffect copy() {
        return new KavaronHarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RobotToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                new SacrificeTargetEffect()
                        .setTargetPointer(new FixedTargets(token, game))
                        .setText("sacrifice that token")
        ), source);
        return true;
    }
}
