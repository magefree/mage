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
package mage.util;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.SpellAbility;
import mage.game.stack.Spell;
import mage.target.Target;


/**
 * @author duncancmt
 */
public class SpellTargetAddress {
    protected int spellAbilityIndex;
    protected UUID mode;
    protected int targetIndex;

    public SpellTargetAddress(int spellAbilityIndex, UUID mode, int targetIndex) {
        this.spellAbilityIndex = spellAbilityIndex;
        this.mode = mode;
        this.targetIndex = targetIndex;
    }

    protected static class SpellTargetAddressIterable implements Iterable<SpellTargetAddress> {
        protected final Spell spell;
        
        public SpellTargetAddressIterable(Spell spell) {
            this.spell = spell;
        }

        public Iterator<SpellTargetAddress> iterator() {
            return new SpellTargetAddressIterator(spell);
        }
    }

    protected static class SpellTargetAddressIterator implements Iterator<SpellTargetAddress> {
        protected Iterator<SpellAbility> spellAbilityIterator;
        protected Integer lastSpellAbilityIndex = null;
        protected Iterator<UUID> modeIterator = null;
        protected Modes modes = null;
        protected UUID lastMode = null;
        protected Iterator<Target> targetIterator = null;
        protected Integer lastTargetIndex = null;

        public SpellTargetAddressIterator(Spell spell) {
            this.spellAbilityIterator = spell.getSpellAbilities().iterator();
            calcNext();
        }

        public boolean hasNext() {
            return lastTargetIndex != null;
        }
        
        public SpellTargetAddress next() {
            SpellTargetAddress ret = new SpellTargetAddress(lastSpellAbilityIndex,
                                                            lastMode,
                                                            lastTargetIndex);
            calcNext();
            return ret;
            
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        protected void calcNext() {
            if (targetIterator == null) {
                if (modeIterator == null) {
                    if (spellAbilityIterator.hasNext()) {
                        if (lastSpellAbilityIndex == null) {
                            lastSpellAbilityIndex = 0;
                        } else {
                            lastSpellAbilityIndex++;
                        }
                        modes = spellAbilityIterator.next().getModes();
                        modeIterator = modes.getSelectedModes().iterator();
                    } else {
                        lastSpellAbilityIndex = null;
                        return;
                    }
                }
                
                if (modeIterator != null && modeIterator.hasNext()) {
                    lastMode = modeIterator.next();
                    targetIterator = modes.get(lastMode).getTargets().iterator();
                } else {
                    lastMode = null;
                    modes = null;
                    modeIterator = null;
                    calcNext();
                }
            }

            if (targetIterator != null && targetIterator.hasNext()) {
                if (lastTargetIndex == null) {
                    lastTargetIndex = 0;
                } else {
                    lastTargetIndex++;
                }
                targetIterator.next();
            } else {
                targetIterator = null;
                lastTargetIndex = null;
                calcNext();
            }
        }
    }


    public static Iterable<SpellTargetAddress> walk(Spell spell) {
        return new SpellTargetAddressIterable(spell);
    }

    public Target getTarget(Spell spell) {
        return spell.getSpellAbilities().get(spellAbilityIndex).getModes().get(mode).getTargets().get(targetIndex);
    }

    public Mode getMode(Spell spell) {
        return spell.getSpellAbilities().get(spellAbilityIndex).getModes().get(mode);
    }

    public SpellAbility getSpellAbility(Spell spell) {
        return spell.getSpellAbilities().get(spellAbilityIndex);
    }
}
