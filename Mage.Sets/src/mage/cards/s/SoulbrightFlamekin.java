
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SoulbrightFlamekin extends CardImpl {

    public SoulbrightFlamekin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}: Target creature gains trample until end of turn. If this is the third time this ability has resolved this turn, you may add {R}{R}{R}{R}{R}{R}{R}{R}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{2}"));
        ability.addEffect(new SoulbrightFlamekinEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);            
    }

    public SoulbrightFlamekin(final SoulbrightFlamekin card) {
        super(card);
    }

    @Override
    public SoulbrightFlamekin copy() {
        return new SoulbrightFlamekin(this);
    }
}

class SoulbrightFlamekinEffect extends OneShotEffect {

    static class ActivationInfo {
        public int zoneChangeCounter;
        public int turn;
        public int activations;
    }

    public SoulbrightFlamekinEffect() {
        super(Outcome.Damage);
        this.staticText = "If this is the third time this ability has resolved this turn, you may add {R}{R}{R}{R}{R}{R}{R}{R}";
    }

    public SoulbrightFlamekinEffect(final SoulbrightFlamekinEffect effect) {
        super(effect);        
    }

    @Override
    public SoulbrightFlamekinEffect copy() {
        return new SoulbrightFlamekinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            ActivationInfo info;
            Object object = game.getState().getValue(source.getSourceId() + "ActivationInfo");
            if (object instanceof ActivationInfo) {
                info = (ActivationInfo) object;
                if (info.turn != game.getTurnNum() || sourcePermanent.getZoneChangeCounter(game) != info.zoneChangeCounter) {
                    info.turn = game.getTurnNum();
                    info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                    info.activations = 0;
                }
            } else {
                info = new ActivationInfo();
                info.turn = game.getTurnNum();
                info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                game.getState().setValue(source.getSourceId() + "ActivationInfo", info);
            }
            info.activations++;
            if (info.activations == 3) {
                controller.getManaPool().addMana(Mana.RedMana(8), game, source);
            }
            return true;
        }
        return false;
    }
}
