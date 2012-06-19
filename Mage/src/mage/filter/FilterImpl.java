/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.filter;

import mage.Constants.CardType;
import mage.ObjectColor;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class FilterImpl<E, T extends FilterImpl<E, T>> implements Filter<E> {

    protected static ListComparer<CardType> compCardType = new ListComparer<CardType>();
    protected static ListComparer<String> compString = new ListComparer<String>();

    protected String message;
    protected boolean notFilter = false;

    @Override
    public abstract FilterImpl<E, T> copy();

    public FilterImpl(String name) {
        this.message = name;
    }

    public FilterImpl(FilterImpl filter) {
        this.message = filter.message;
        this.notFilter = filter.notFilter;
    }

    protected boolean compareInts(int int1, int int2, ComparisonType type) {
        switch (type) {
            case Equal:
                if (int1 != int2)
                    return false;
                break;
            case GreaterThan:
                if (int1 <= int2)
                    return false;
                break;
            case LessThan:
                if (int1 >= int2)
                    return false;
                break;
        }
        return true;
    }

    protected boolean compareColors(ObjectColor color1, ObjectColor color2, ComparisonScope scope) {
        if (scope == ComparisonScope.All)
            return color2.equals(color1);
        else
            return color2.contains(color1);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setNotFilter(boolean notFilter) {
        this.notFilter = notFilter;
    }

}
