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
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ScrapTrawler extends CardImpl {

    public ScrapTrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add("Construct");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Scrap Trawler or another artifact you control is put into a graveyard from the battlefield,
        // return to your hand target artifact card in your graveyard with lesser converted mana cost.
        Ability ability = new ScrapTrawlerTriggeredAbility();
        ability.addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card in your graveyard with lesser converted mana cost")));
        this.addAbility(ability);
    }

    public ScrapTrawler(final ScrapTrawler card) {
        super(card);
    }

    @Override
    public ScrapTrawler copy() {
        return new ScrapTrawler(this);
    }
}

class ScrapTrawlerTriggeredAbility extends TriggeredAbilityImpl {

    public ScrapTrawlerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect());
        getEffects().get(0).setText("return to your hand target artifact card in your graveyard with lesser converted mana cost");
    }

    public ScrapTrawlerTriggeredAbility(final ScrapTrawlerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScrapTrawlerTriggeredAbility copy() {
        return new ScrapTrawlerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent.getControllerId().equals(this.getControllerId()) && permanent.isArtifact()) {
                FilterCard filter = new FilterArtifactCard("artifact card in your graveyard with converted mana cost less than " + permanent.getManaCost().convertedManaCost());
                filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, permanent.getManaCost().convertedManaCost()));
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
                getTargets().clear();
                addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another artifact you control is put into a graveyard from the battlefield, " + super.getRule();
    }

}
