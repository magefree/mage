package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GenerousPlunderer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactPermanent("artifacts permanents controlled by defending player");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public GenerousPlunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of your upkeep, you may create a Treasure token. When you do, target opponent creates a tapped Treasure token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GenerousPlundererEffect(), TargetController.YOU, true));

        // Whenever Generous Plunderer attacks, it deals damage to defending player equal to the number of artifacts they control.
        this.addAbility(new AttacksTriggeredAbility(
                new DamageTargetEffect(xValue), false,
                "Whenever {this} attacks, it deals damage to defending player equal to the number of artifacts they control.",
                SetTargetPointer.PLAYER
        ));
    }

    private GenerousPlunderer(final GenerousPlunderer card) {
        super(card);
    }

    @Override
    public GenerousPlunderer copy() {
        return new GenerousPlunderer(this);
    }
}

class GenerousPlundererEffect extends OneShotEffect {

    GenerousPlundererEffect() {
        super(Outcome.Benefit);
        staticText = "you may create a Treasure token. When you do, target opponent creates a tapped Treasure token.";
    }

    private GenerousPlundererEffect(final GenerousPlundererEffect effect) {
        super(effect);
    }

    @Override
    public GenerousPlundererEffect copy() {
        return new GenerousPlundererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = new CreateTokenEffect(new TreasureToken())
                .apply(game, source);
        if (!flag) {
            return false;
        }
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new CreateTokenTargetEffect(new TreasureToken(), 1, true), false
        );
        reflexive.addTarget(new TargetOpponent());
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }

}