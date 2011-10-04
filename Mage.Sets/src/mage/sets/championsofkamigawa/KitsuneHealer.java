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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public class KitsuneHealer extends CardImpl<KitsuneHealer> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.getSupertype().add("Legendary");
        filter.setScopeSupertype(Filter.ComparisonScope.Any);
    }

    public KitsuneHealer(UUID ownerId) {
        super(ownerId, 27, "Kitsune Healer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Fox");
        this.subtype.add("Cleric");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability firstAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new PreventDamageTargetEffect(Constants.Duration.EndOfTurn, 1), new TapSourceCost());
        firstAbility.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new KitsuneHealerEffect(), new TapSourceCost());
        secondAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(secondAbility);
    }

    public KitsuneHealer(final KitsuneHealer card) {
        super(card);
    }

    @Override
    public KitsuneHealer copy() {
        return new KitsuneHealer(this);
    }

}

class KitsuneHealerEffect extends PreventionEffectImpl<KitsuneHealerEffect> {

	public KitsuneHealerEffect() {
		super(Constants.Duration.EndOfTurn);
		staticText = "Prevent all damage that would be dealt to target legendary creature this turn";
	}

	public KitsuneHealerEffect(final KitsuneHealerEffect effect) {
		super(effect);
	}

	@Override
	public KitsuneHealerEffect copy() {
		return new KitsuneHealerEffect();
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
		if (!game.replaceEvent(preventEvent)) {
			int damage = event.getAmount();
			event.setAmount(0);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
		}
		return false;
	}

}
