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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public class MercenaryInformer extends CardImpl {
    
    private static final FilterObject filterBlack = new FilterStackObject("black spells or abilities from black sources");
    private static final FilterPermanent filterMercenary = new FilterPermanent("nontoken Mercenary");

    static {
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterMercenary.add(Predicates.not(new TokenPredicate()));
        filterMercenary.add(new SubtypePredicate(SubType.MERCENARY));
    }

    public MercenaryInformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add("Human");
        this.subtype.add("Rebel");
        this.subtype.add("Mercenary");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Mercenary Informer can't be the target of black spells or abilities from black sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(filterBlack, Duration.WhileOnBattlefield)));
        
        // {2}{W}: Put target nontoken Mercenary on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false), new ManaCostsImpl("{2}{W}"));
        ability.addTarget(new TargetPermanent(filterMercenary));
        this.addAbility(ability);
    }

    public MercenaryInformer(final MercenaryInformer card) {
        super(card);
    }

    @Override
    public MercenaryInformer copy() {
        return new MercenaryInformer(this);
    }
}
