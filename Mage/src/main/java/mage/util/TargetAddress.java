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
import java.util.Objects;
import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.game.stack.Spell;
import mage.target.Target;

/**
 * @author duncant
 */
public class TargetAddress {

    protected int spellAbilityIndex;
    protected UUID mode;
    protected int targetIndex;

    public TargetAddress(int spellAbilityIndex, UUID mode, int targetIndex) {
        this.spellAbilityIndex = spellAbilityIndex;
        this.mode = mode;
        this.targetIndex = targetIndex;
    }

    protected static class TargetAddressIterable implements Iterable<TargetAddress> {

        protected final Card card;

        public TargetAddressIterable(Card card) {
            this.card = card;
        }

        @Override
        public Iterator<TargetAddress> iterator() {
            return new TargetAddressIterator(card);
        }
    }

    protected static class TargetAddressIterator implements Iterator<TargetAddress> {

        protected Iterator<SpellAbility> spellAbilityIterator;
        protected Integer lastSpellAbilityIndex = null;
        protected Iterator<Mode> modeIterator = null;
        protected Modes modes = null;
        protected UUID lastMode = null;
        protected Iterator<Target> targetIterator = null;
        protected Integer lastTargetIndex = null;

        public TargetAddressIterator(Spell spell) {
            this.spellAbilityIterator = spell.getSpellAbilities().iterator();
            calcNext();
        }

        public TargetAddressIterator(Card card) {
            this.lastSpellAbilityIndex = 0;
            this.spellAbilityIterator = null;
            this.modes = card.getSpellAbility().getModes();
            this.modeIterator = this.modes.getSelectedModes().iterator();
            calcNext();
        }

        @Override
        public boolean hasNext() {
            return lastTargetIndex != null;
        }

        @Override
        public TargetAddress next() {
            TargetAddress ret = new TargetAddress(lastSpellAbilityIndex,
                    lastMode,
                    lastTargetIndex);
            calcNext();
            return ret;

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        protected void calcNext() {
            if (targetIterator == null) {
                if (modeIterator == null) {
                    if (spellAbilityIterator != null && spellAbilityIterator.hasNext()) {
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
                    lastMode = modeIterator.next().getId();
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

    public static Iterable<TargetAddress> walk(Card card) {
        return new TargetAddressIterable(card);
    }

    public Target getTarget(Spell spell) {
        return getMode(spell).getTargets().get(targetIndex);
    }

    public Target getTarget(Card card) {
        return getMode(card).getTargets().get(targetIndex);
    }

    public Mode getMode(Spell spell) {
        return getSpellAbility(spell).getModes().get(mode);
    }

    public Mode getMode(Card card) {
        return getSpellAbility(card).getModes().get(mode);
    }

    public SpellAbility getSpellAbility(Spell spell) {
        return spell.getSpellAbilities().get(spellAbilityIndex);
    }

    public SpellAbility getSpellAbility(Card card) {
        if (spellAbilityIndex > 0) {
            throw new IndexOutOfBoundsException("SpellAbility index " + spellAbilityIndex + " is out of bounds.");
        }
        return card.getSpellAbility();
    }

    public boolean equals(TargetAddress other) {
        return spellAbilityIndex == other.spellAbilityIndex
                && mode.equals(other.mode)
                && targetIndex == other.targetIndex;
    }

    @Override
    public int hashCode() {
        return spellAbilityIndex ^ mode.hashCode() ^ targetIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TargetAddress other = (TargetAddress) obj;
        if (this.spellAbilityIndex != other.spellAbilityIndex) {
            return false;
        }
        if (this.targetIndex != other.targetIndex) {
            return false;
        }
        return Objects.equals(this.mode, other.mode);
    }
}
