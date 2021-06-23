package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.PurphorossInterventionToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PurphorossIntervention extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, 2);

    public PurphorossIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Choose one —
        // • Create an X/1 red Elemental creature token with trample and haste. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new PurphorossInterventionEffect());

        // • Purphoros's Intervention deals twice X damage to target creature or planeswalker.
        Mode mode = new Mode(new DamageTargetEffect(xValue)
                .setText("{this} deals twice X damage to target creature or planeswalker"));
        mode.addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addMode(mode);
    }

    private PurphorossIntervention(final PurphorossIntervention card) {
        super(card);
    }

    @Override
    public PurphorossIntervention copy() {
        return new PurphorossIntervention(this);
    }
}

class PurphorossInterventionEffect extends OneShotEffect {

    PurphorossInterventionEffect() {
        super(Outcome.Benefit);
        staticText = "Create an X/1 red Elemental creature token with trample and haste. " +
                "Sacrifice it at the beginning of the next end step.";
    }

    private PurphorossInterventionEffect(final PurphorossInterventionEffect effect) {
        super(effect);
    }

    @Override
    public PurphorossInterventionEffect copy() {
        return new PurphorossInterventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new PurphorossInterventionToken(source.getManaCostsToPay().getX());
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        token.getLastAddedTokenIds()
                .stream()
                .forEach(uuid -> game.addDelayedTriggeredAbility(
                        new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                                new SacrificeTargetEffect()
                                        .setText("sacrifice this creature")
                                        .setTargetPointer(new FixedTarget(uuid, game))
                        ), source
                ));
        return true;
    }
}