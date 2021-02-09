/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class SealedFate extends CardImpl {

    public SealedFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}{B}");

        // Look at the top X cards of target opponent's library. Exile one of those cards and put the rest back on top of that player's library in any order.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(ManacostVariableValue.instance,
                                              false, StaticValue.get(1),
                                              new FilterCard("a card to exile"), Zone.LIBRARY, true,
                                              false, false, Zone.EXILED,
                                              false, true, true));
    }

    private SealedFate(final SealedFate card) {
        super(card);
    }

    @Override
    public SealedFate copy() {
        return new SealedFate(this);
    }
}
