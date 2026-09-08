package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.util.functions.CopyApplier;

import java.util.*;

/**
 * @author miesma
 */
public final class TaskmasterMercenaryMimic extends CardImpl {

    public TaskmasterMercenaryMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Photographic Reflexes
        // At the beginning of your first main phase, until your next turn,
        // Taskmaster becomes a copy of up to one target creature on the battlefield or creature card in a graveyard,
        // except his name is Taskmaster, Mercenary Mimic and he’s a legendary Human Mercenary Villain creature.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(new TaskmasterMercenaryMimicCopyEffect(),false)
                .withFlavorWord("Photographic Reflexes");
        ability.addTarget(new TargetCardInGraveyardBattlefieldOrStack(0,1,
                StaticFilters.FILTER_CARD_CREATURE,StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);


    }

    private TaskmasterMercenaryMimic(final TaskmasterMercenaryMimic card) {
        super(card);
    }

    @Override
    public TaskmasterMercenaryMimic copy() {
        return new TaskmasterMercenaryMimic(this);
    }
}

class TaskmasterMercenaryMimicCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.setName("Taskmaster, Mercenary Mimic");
        blueprint.removeAllCreatureTypes();
        blueprint.addSuperType(SuperType.LEGENDARY);
        blueprint.addSubType(SubType.HUMAN);
        blueprint.addSubType(SubType.MERCENARY);
        blueprint.addSubType(SubType.VILLAIN);
        return true;
    }
}

class TaskmasterMercenaryMimicCopyEffect extends OneShotEffect {

    TaskmasterMercenaryMimicCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "until your next turn, {this} becomes a copy of of up to one target creature on the battlefield or creature card in a graveyard, "
                + "except his name is Taskmaster, Mercenary Mimic, and he's a legendary Human Mercenary Villain creature";
    }

    private TaskmasterMercenaryMimicCopyEffect(final TaskmasterMercenaryMimicCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        UUID targetID = source.getFirstTarget();
        if (player != null && targetID != null) {
            Zone zone = game.getState().getZone(targetID);
            if (zone != null && zone.match(Zone.BATTLEFIELD)) {
                // Copy from Permanent (not card)
                CopyPermanentEffect copyPermanentEffect = new CopyPermanentEffect(
                        new FilterCreaturePermanent(), new TaskmasterMercenaryMimicCopyApplier(), true
                ).setDuration(Duration.UntilYourNextTurn);
                copyPermanentEffect.apply(game, source);
            } else if (zone != null && zone.match(Zone.GRAVEYARD)) {
                // Copy from Card
                Card copyFromCard = game.getCard(targetID);
                if (copyFromCard != null) {
                    Permanent newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                    newBluePrint.assignNewId();
                    TaskmasterMercenaryMimicCopyApplier applier = new TaskmasterMercenaryMimicCopyApplier();
                    applier.apply(game, newBluePrint, source, source.getSourceId());
                    CopyEffect copyEffect = new CopyEffect(Duration.UntilYourNextTurn, newBluePrint, source.getSourceId());
                    game.addEffect(copyEffect, source);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public TaskmasterMercenaryMimicCopyEffect copy() {
        return new TaskmasterMercenaryMimicCopyEffect(this);
    }
}
