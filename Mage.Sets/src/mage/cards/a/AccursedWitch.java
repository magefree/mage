
package mage.cards.a;

import java.util.UUID;

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
import mage.util.CardUtil;

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
        this.addAbility(new DiesTriggeredAbility(new AccursedWitchReturnTransformedEffect()));
    }

    public AccursedWitch(final AccursedWitch card) {
        super(card);
    }

    @Override
    public AccursedWitch copy() {
        return new AccursedWitch(this);
    }
}

class AccursedWitchReturnTransformedEffect extends OneShotEffect {

    public AccursedWitchReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put {this} from your graveyard onto the battlefield transformed";
    }

    public AccursedWitchReturnTransformedEffect(final AccursedWitchReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public AccursedWitchReturnTransformedEffect copy() {
        return new AccursedWitchReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
                //note: should check for null after game.getCard
                Card card = game.getCard(source.getSourceId());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class AccursedWitchSpellsCostReductionEffect extends CostModificationEffectImpl {

    public AccursedWitchSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.REDUCE_COST);
        this.staticText = "Spells your opponents cast that target {this} cost {1} less to cast.";
    }

    protected AccursedWitchSpellsCostReductionEffect(AccursedWitchSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
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
            }
        }
        return false;
    }

    @Override
    public AccursedWitchSpellsCostReductionEffect copy() {
        return new AccursedWitchSpellsCostReductionEffect(this);
    }
}
