/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.constants.Zone;
import mage.abilities.StaticAbility;

/**
 * OLD RULES:
 * 700.4. If a permanent is indestructible, rules and effects can't destroy it. (See rule 701.6, "Destroy.")
 * Such permanents are not destroyed by lethal damage, and they ignore the lethal-damage state-based action
 * (see rule 704.5g). Rules or effects may cause an indestructible permanent to be sacrificed, put into a
 * graveyard, or exiled. #
 *
 *   700.4a Although the text "[This permanent] is indestructible" is an ability, actually being
 *   indestructible is neither an ability nor a characteristic. It's just something that's true
 *   about a permanent.
 *
 * NEW RULES
 *
 *
 *
 *
 *
 * 
 * @author BetaSteward_at_googlemail.com
 */

public class IndestructibleAbility extends StaticAbility<IndestructibleAbility> {

    private static final IndestructibleAbility fINSTANCE;

    static {
        fINSTANCE = new IndestructibleAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static IndestructibleAbility getInstance() {
        return fINSTANCE;
    }

    private IndestructibleAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public IndestructibleAbility copy() {
        return fINSTANCE;
    }

    @Override
    public String getRule() {
        return "{this} is indestructible.";
    }

}
