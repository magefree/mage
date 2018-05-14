/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.predicate.permanent.BlockingPredicate;

/**
 *
 * @author rystan
 */
public class Electryte extends CardImpl {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature();

    static {
        filter.add(new BlockingPredicate());
    }
    
    public Electryte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Electryte successfully deals combat damage to defending player, 
        // Electryte deals damage equal to its power to each blocking creature.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DamageAllEffect(this.power.getValue(), filter), false, false));
    }

    public Electryte(final Electryte card) {
        super(card);
    }

    @Override
    public Electryte copy() {
        return new Electryte(this);
    }
}
