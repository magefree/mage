
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author halljared
 */
public final class AccursedWitch extends CardImpl {

    public AccursedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.i.InfectiousCurse.class;

        // Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AccursedWitchSpellsCostReductionEffect()));
        // When Accursed Witch dies, return it to the battlefield transformed under your control attached to target opponent.
        this.addAbility(new TransformAbility());
        Ability ability = new DiesTriggeredAbility(new AccursedWitchReturnTransformedEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AccursedWitch(final AccursedWitch card) {
        super(card);
    }

    @Override
    public AccursedWitch copy() {
        return new AccursedWitch(this);
    }
}

class AccursedWitchReturnTransformedEffect extends OneShotEffect {

    AccursedWitchReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put {this} from your graveyard onto the battlefield transformed under your control attached to target opponent";
    }

    private AccursedWitchReturnTransformedEffect(final AccursedWitchReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public AccursedWitchReturnTransformedEffect copy() {
        return new AccursedWitchReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attachTo = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || !(game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) || attachTo == null) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        UUID secondFaceId = game.getCard(source.getSourceId()).getSecondCardFace().getId();
        game.getState().setValue("attachTo:" + secondFaceId, attachTo.getId());
        //note: should check for null after game.getCard
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                attachTo.addAttachment(card.getId(), game);
            }
        }
        return true;
    }
}

class AccursedWitchSpellsCostReductionEffect extends CostModificationEffectImpl {

    AccursedWitchSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.REDUCE_COST);
        this.staticText = "Spells your opponents cast that target {this} cost {1} less to cast.";
    }

    private AccursedWitchSpellsCostReductionEffect(AccursedWitchSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility) || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
            Mode mode = abilityToModify.getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetUUID : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetUUID);
                    if (permanent != null && permanent.getId().equals(source.getSourceId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public AccursedWitchSpellsCostReductionEffect copy() {
        return new AccursedWitchSpellsCostReductionEffect(this);
    }
}
