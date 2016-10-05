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
package mage.sets;

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;
import java.util.List;

/**
 *
 * @author fireshoes
 */

public class Champs extends ExpansionSet {

    private static final Champs fINSTANCE = new Champs();

    public static Champs getInstance() {
        return fINSTANCE;
    }

    private Champs() {
        super("Champs", "CP", "mage.sets.champs", new GregorianCalendar(2006, 3, 18).getTime(), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        cards.add(new SetCardInfo("Blood Knight", 7, Rarity.SPECIAL, mage.cards.b.BloodKnight.class));
        cards.add(new SetCardInfo("Bramblewood Paragon", 11, Rarity.SPECIAL, mage.cards.b.BramblewoodParagon.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 10, Rarity.SPECIAL, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Electrolyze", 1, Rarity.SPECIAL, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Groundbreaker", 8, Rarity.SPECIAL, mage.cards.g.Groundbreaker.class));
        cards.add(new SetCardInfo("Imperious Perfect", 9, Rarity.SPECIAL, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Mutavault", 12, Rarity.SPECIAL, mage.cards.m.Mutavault.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 2, Rarity.SPECIAL, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Rakdos Guildmage", 3, Rarity.SPECIAL, mage.cards.r.RakdosGuildmage.class));
        cards.add(new SetCardInfo("Serra Avenger", 6, Rarity.SPECIAL, mage.cards.s.SerraAvenger.class));
        cards.add(new SetCardInfo("Urza's Factory", 5, Rarity.SPECIAL, mage.cards.u.UrzasFactory.class));
        cards.add(new SetCardInfo("Voidslime", 4, Rarity.SPECIAL, mage.cards.v.Voidslime.class));
    }
}
