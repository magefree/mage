
package mage.cards.e;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Ketsuban
 */
public final class EverythingamajigC extends CardImpl {

    public EverythingamajigC(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Mana Screw
        // 1: Flip a coin. If you win the flip, add CC to your mana pool. Activate this ability only any time you could cast an instant.
        this.addAbility(new ManaScrewAbility());

        // Disrupting Scepter
        // 3, T: Target player discards a card. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new GenericManaCost(3), MyTurnCondition.instance);
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Chimeric Staff
        // X: Everythingamajig becomes an X/X Construct artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChimericStaffEffect(), new VariableManaCost()));
    }

    public EverythingamajigC(final EverythingamajigC card) {
        super(card);
    }

    @Override
    public EverythingamajigC copy() {
        return new EverythingamajigC(this);
    }
}

class ManaScrewAbility extends ActivatedManaAbilityImpl {

    public ManaScrewAbility() {
        super(Zone.BATTLEFIELD, new ManaScrewEffect(), new GenericManaCost(1));
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 2, 0, 0));
    }

    public ManaScrewAbility(final ManaScrewAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public ManaScrewAbility copy() {
        return new ManaScrewAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class ManaScrewEffect extends BasicManaEffect {

    public ManaScrewEffect() {
        super(Mana.ColorlessMana(2));
        this.staticText = "Flip a coin. If you win the flip, add {C}{C}";
    }

    public ManaScrewEffect(final ManaScrewEffect effect) {
        super(effect);
        this.manaTemplate = effect.manaTemplate.copy();
    }

    @Override
    public ManaScrewEffect copy() {
        return new ManaScrewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.flipCoin(source, game, true)) {
            player.getManaPool().addMana(getMana(game, source), game, source);
        }
        return true;
    }
}

class ChimericStaffEffect extends ContinuousEffectImpl {

    public ChimericStaffEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        setText();
    }

    public ChimericStaffEffect(final ChimericStaffEffect effect) {
        super(effect);
    }

    @Override
    public ChimericStaffEffect copy() {
        return new ChimericStaffEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.addCardType(CardType.CREATURE);
                        permanent.getSubtype(game).add(SubType.CONSTRUCT);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int xValue = source.getManaCostsToPay().getX();
                        if (xValue != 0) {
                            permanent.getPower().setValue(xValue);
                            permanent.getToughness().setValue(xValue);
                        }
                    }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        staticText = duration.toString() + " {this} becomes an X/X Construct artifact creature";
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
