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

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class MasterpieceSeriesAmonkhet extends ExpansionSet {

    private static final MasterpieceSeriesAmonkhet instance = new MasterpieceSeriesAmonkhet();

    public static MasterpieceSeriesAmonkhet getInstance() {
        return instance;
    }

    private MasterpieceSeriesAmonkhet() {
        super("Masterpiece Series Amonkhet", "MPS-AKH", ExpansionSet.buildDate(2017, 4, 28), SetType.PROMOTIONAL);
        this.blockName = "Masterpiece Series";
        this.hasBoosters = false;
        this.hasBasicLands = false;
        CardGraphicInfo cardGraphicInfo = new CardGraphicInfo(FrameStyle.KLD_INVENTION, false);

        cards.add(new SetCardInfo("Aggravated Assault", 25, Rarity.SPECIAL, mage.cards.a.AggravatedAssault.class));
        cards.add(new SetCardInfo("Attrition", 19, Rarity.SPECIAL, mage.cards.a.Attrition.class));
        cards.add(new SetCardInfo("Austere Command", 1, Rarity.SPECIAL, mage.cards.a.AustereCommand.class));
        cards.add(new SetCardInfo("Avatar of Woe", 38, Rarity.SPECIAL, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Aven Mindcensor", 2, Rarity.SPECIAL, mage.cards.a.AvenMindcensor.class));
        cards.add(new SetCardInfo("Blood Moon", 46, Rarity.SPECIAL, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Bontu the Glorified", 20, Rarity.SPECIAL, mage.cards.b.BontuTheGlorified.class));
        cards.add(new SetCardInfo("Capsize", 32, Rarity.SPECIAL, mage.cards.c.Capsize.class));
        cards.add(new SetCardInfo("Chain Lightning", 26, Rarity.SPECIAL, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Choke", 50, Rarity.SPECIAL, mage.cards.c.Choke.class));
        cards.add(new SetCardInfo("Consecrated Sphinx", 8, Rarity.SPECIAL, mage.cards.c.ConsecratedSphinx.class));
        cards.add(new SetCardInfo("Containment Priest", 3, Rarity.SPECIAL, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Counterbalance", 9, Rarity.SPECIAL, mage.cards.c.Counterbalance.class));
        cards.add(new SetCardInfo("Counterspell", 10, Rarity.SPECIAL, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Cryptic Command", 11, Rarity.SPECIAL, mage.cards.c.CrypticCommand.class));
        cards.add(new SetCardInfo("Damnation", 39, Rarity.SPECIAL, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Dark Ritual", 21, Rarity.SPECIAL, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Daze", 12, Rarity.SPECIAL, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Desolation Angel", 40, Rarity.SPECIAL, mage.cards.d.DesolationAngel.class));
        cards.add(new SetCardInfo("Diabolic Edict", 41, Rarity.SPECIAL, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Diabolic Intent", 22, Rarity.SPECIAL, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Divert", 13, Rarity.SPECIAL, mage.cards.d.Divert.class));
        cards.add(new SetCardInfo("Entomb", 23, Rarity.SPECIAL, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Forbid", 33, Rarity.SPECIAL, mage.cards.f.Forbid.class));
        cards.add(new SetCardInfo("Force of Will", 14, Rarity.SPECIAL, mage.cards.f.ForceOfWill.class));
        cards.add(new SetCardInfo("Hazoret the Fervent", 27, Rarity.SPECIAL, mage.cards.h.HazoretTheFervent.class));
        cards.add(new SetCardInfo("Kefnet the Mindful", 15, Rarity.SPECIAL, mage.cards.k.KefnetTheMindful.class));
        cards.add(new SetCardInfo("Lord of Extinction", 52, Rarity.SPECIAL, mage.cards.l.LordOfExtinction.class));
        cards.add(new SetCardInfo("Loyal Retainers", 4, Rarity.SPECIAL, mage.cards.l.LoyalRetainers.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 29, Rarity.SPECIAL, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mind Twist", 24, Rarity.SPECIAL, mage.cards.m.MindTwist.class));
        cards.add(new SetCardInfo("No Mercy", 43, Rarity.SPECIAL, mage.cards.n.NoMercy.class));
        cards.add(new SetCardInfo("Oketra the True", 5, Rarity.SPECIAL, mage.cards.o.OketraTheTrue.class));
        cards.add(new SetCardInfo("Opposition", 35, Rarity.SPECIAL, mage.cards.o.Opposition.class));
        cards.add(new SetCardInfo("Pact of Negation", 16, Rarity.SPECIAL, mage.cards.p.PactOfNegation.class));
        cards.add(new SetCardInfo("Rhonas the Indomitable", 28, Rarity.SPECIAL, mage.cards.r.RhonasTheIndomitable.class));
        cards.add(new SetCardInfo("Shatterstorm", 48, Rarity.SPECIAL, mage.cards.s.Shatterstorm.class));
        cards.add(new SetCardInfo("Slaughter Pact", 44, Rarity.SPECIAL, mage.cards.s.SlaughterPact.class));
        cards.add(new SetCardInfo("Spell Pierce", 17, Rarity.SPECIAL, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Stifle", 18, Rarity.SPECIAL, mage.cards.s.Stifle.class));
        cards.add(new SetCardInfo("Vindicate", 30, Rarity.SPECIAL, mage.cards.v.Vindicate.class));
        cards.add(new SetCardInfo("Worship", 6, Rarity.SPECIAL, mage.cards.w.Worship.class));
        cards.add(new SetCardInfo("Wrath of God", 7, Rarity.SPECIAL, mage.cards.w.WrathOfGod.class));
    }
}