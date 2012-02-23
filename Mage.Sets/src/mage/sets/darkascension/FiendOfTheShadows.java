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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public class FiendOfTheShadows extends CardImpl<FiendOfTheShadows> {

    private UUID exileId = UUID.randomUUID();

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a human");

    static {
        filter.getSubtype().add("Human");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }
    
    public FiendOfTheShadows(UUID ownerId) {
        super(ownerId, 62, "Fiend of the Shadows", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Vampire");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Fiend of the Shadows deals combat damage to a player, that player exiles a card from his or her hand. You may play that card for as long as it remains exiled.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExileFromZoneTargetEffect(Constants.Zone.HAND, exileId, "Fiend of the Shadows", new FilterCard()), false));
		this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new FiendOfTheShadowsEffect(exileId)));        
        
        // Sacrifice a Human: Regenerate Fiend of the Shadows.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))));
    }

    public FiendOfTheShadows(final FiendOfTheShadows card) {
        super(card);
    }

    @Override
    public FiendOfTheShadows copy() {
        return new FiendOfTheShadows(this);
    }
}

class FiendOfTheShadowsEffect extends AsThoughEffectImpl<FiendOfTheShadowsEffect> {

    private UUID exileId;
    
	public FiendOfTheShadowsEffect(UUID exileId) {
		super(Constants.AsThoughEffectType.CAST, Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        this.exileId = exileId;
		staticText = "You may play that card for as long as it remains exiled";
	}

	public FiendOfTheShadowsEffect(final FiendOfTheShadowsEffect effect) {
		super(effect);
        this.exileId = effect.exileId;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public FiendOfTheShadowsEffect copy() {
		return new FiendOfTheShadowsEffect(this);
	}

	@Override
	public boolean applies(UUID sourceId, Ability source, Game game) {
		Card card = game.getCard(sourceId);
		if (card != null) {
            if (game.getExile().getExileZone(exileId).contains(card.getId()))
                return true;
		}
		return false;
	}

}