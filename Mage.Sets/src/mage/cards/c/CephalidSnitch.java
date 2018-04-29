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
package mage.cards.c;

import java.awt.*;
import java.util.*;
import java.util.List;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author tcontis
 */
public class CephalidSnitch extends CardImpl {

    public CephalidSnitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Cephalid Snitch: Target creature loses protection from black until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CephalidSnitchEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CephalidSnitch(final CephalidSnitch card) {
        super(card);
    }

    @Override
    public CephalidSnitch copy() {
        return new CephalidSnitch(this);
    }
}
class CephalidSnitchEffect extends LoseAbilityTargetEffect{

    public CephalidSnitchEffect() {
        super(ProtectionAbility.from(ObjectColor.BLACK), Duration.EndOfTurn);
        staticText = "Target creature loses protection from black until end of turn.";
    }

    public CephalidSnitchEffect(final CephalidSnitchEffect effect) {
        super(effect);
    }

    @Override
    public CephalidSnitchEffect copy() {
        return new CephalidSnitchEffect(this);
    }

    public String filterNameAssembler(List<ObjectColor> unsortedColors) {
        //Order colors properly by WUBRG (skipping black of course) and construct string to be displayed for ability.
        List<ObjectColor> colors = new ArrayList<>();
        if(unsortedColors.contains(ObjectColor.WHITE)){
            colors.add(ObjectColor.WHITE);
        }
        if(unsortedColors.contains(ObjectColor.BLUE)){
            colors.add(ObjectColor.BLUE);
        }
        if(unsortedColors.contains(ObjectColor.RED)){
            colors.add(ObjectColor.RED);
        }
        if(unsortedColors.contains(ObjectColor.GREEN)){
            colors.add(ObjectColor.GREEN);
        }
        if (colors.size() == 1) {
            return colors.get(0).getDescription();
        } else if (colors.size() == 2) {
            return colors.get(0).getDescription() + " and from " + colors.get(1).getDescription();
        } else if (colors.size() == 3) {
            return colors.get(0).getDescription() + ", from " + colors.get(1).getDescription() + " and from " + colors.get(2).getDescription();
        } else if (colors.size() == 4) {
            return colors.get(0).getDescription() + ", from " + colors.get(1).getDescription() + ", from " + colors.get(2).getDescription() + " and  from " + colors.get(3).getDescription();
        }
        return "";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {

            //Go through protection abilities and sort out any containing black, then record the colors other than black
            for(ProtectionAbility a: targetCreature.getAbilities().getProtectionAbilities()) {
                List<ObjectColor> objectColors = new ArrayList<>();
                if(a.getColors().contains(ObjectColor.BLACK))
                    for (ObjectColor o : a.getColors()) {
                        if (!objectColors.contains(o) && !o.isBlack())
                            objectColors.add(o);
                    }
                //Construct a card filter excluding black
                if(objectColors.size() > 0) {
                    FilterCard filter = new FilterCard(filterNameAssembler(objectColors));
                    if (objectColors.size() == 1)
                        filter.add(new ColorPredicate(objectColors.get(0)));
                    else if (objectColors.size() == 2)
                        filter.add(Predicates.or(new ColorPredicate(objectColors.get(0)), new ColorPredicate(objectColors.get(1))));
                    else if (objectColors.size() == 3)
                        filter.add(Predicates.or(new ColorPredicate(objectColors.get(0)), new ColorPredicate(objectColors.get(1)), new ColorPredicate(objectColors.get(2))));
                    else if (objectColors.size() == 4)
                        filter.add(Predicates.or(new ColorPredicate(objectColors.get(0)), new ColorPredicate(objectColors.get(1)), new ColorPredicate(objectColors.get(2)), new ColorPredicate(objectColors.get(3))));
                    a.setFilter(filter);
                }
            }
            return true;
        }
        return false;
    }
}