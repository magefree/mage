package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GalepowderMage extends CardImpl {

    public GalepowderMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Galepowder Mage attacks, exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new GalepowderMageEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private GalepowderMage(final GalepowderMage card) {
        super(card);
    }

    @Override
    public GalepowderMage copy() {
        return new GalepowderMage(this);
    }
}

class GalepowderMageEffect extends OneShotEffect {

    public GalepowderMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public GalepowderMageEffect(final GalepowderMageEffect effect) {
        super(effect);
    }

    @Override
    public GalepowderMageEffect copy() {
        return new GalepowderMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    UUID exileId = UUID.randomUUID();
                    if (controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getIdName(), source, game, Zone.BATTLEFIELD, true)) {
                        Card card = game.getCard(getTargetPointer().getFirst(game, source));
                        if (card != null) {
                            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                            effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                            game.addDelayedTriggeredAbility(delayedAbility, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
