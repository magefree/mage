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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class WindreaderSphinx extends CardImpl<WindreaderSphinx> {

    public WindreaderSphinx(UUID ownerId) {
        super(ownerId, 81, "Windreader Sphinx", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a creature with flying attacks, you may draw a card.
        this.addAbility(new WindreaderSphinxTriggeredAbility());
    }

    public WindreaderSphinx(final WindreaderSphinx card) {
        super(card);
    }

    @Override
    public WindreaderSphinx copy() {
        return new WindreaderSphinx(this);
    }
}

class WindreaderSphinxTriggeredAbility extends TriggeredAbilityImpl<WindreaderSphinxTriggeredAbility> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WindreaderSphinxTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public WindreaderSphinxTriggeredAbility(final WindreaderSphinxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED ) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            if (attacker != null && filter.match(attacker, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with flying attacks, " + super.getRule();
    }

    @Override
    public WindreaderSphinxTriggeredAbility copy() {
        return new WindreaderSphinxTriggeredAbility(this);
    }
}
