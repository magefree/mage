/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.e;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author rystan
 */
public class Electryte extends CardImpl {

    public Electryte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Electryte successfully deals combat damage to defending player, 
        // Electryte deals damage equal to its power to each blocking creature.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ElectryteEffect(), false));
    }

    public Electryte(final Electryte card) {
        super(card);
    }

    @Override
    public Electryte copy() {
        return new Electryte(this);
    }
}

class ElectryteEffect extends OneShotEffect {
    
    static private FilterBlockingCreature filter = new FilterBlockingCreature();
    
    public ElectryteEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to its power to each blocking creature";        
    }
    
    public ElectryteEffect(final ElectryteEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisCreature = game.getPermanent(source.getSourceId());
        int amount = thisCreature.getPower().getValue();
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            permanent.damage(amount, source.getSourceId(), game, false, true);
        }
        return true;
    }
    
    @Override
    public ElectryteEffect copy () {
        return new ElectryteEffect(this);
    }
}
