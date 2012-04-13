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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NecroticPlague extends CardImpl<NecroticPlague> {

	public NecroticPlague(UUID ownerId) {
		super(ownerId, 107, "Necrotic Plague", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
		this.expansionSetCode = "M11";
		this.color.setBlack(true);
		this.subtype.add("Aura");

		TargetPermanent auraTarget = new TargetCreaturePermanent();
		this.getSpellAbility().addTarget(auraTarget);
		this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
		Ability ability = new EnchantAbility(auraTarget.getTargetName());
		this.addAbility(ability);
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NecroticPlagueEffect(this.objectId)));

	}

	public NecroticPlague(final NecroticPlague card) {
		super(card);
	}

    @Override
    public void assignNewId() {
        super.assignNewId();
        updateSource();
    }

	@Override
	public NecroticPlague copy() {
		return new NecroticPlague(this);
	}
    
    private void updateSource() {
        for (Ability ability: abilities) {
            for (Effect effect: ability.getEffects()) {
                if (effect instanceof NecroticPlagueEffect) {
                    ((NecroticPlagueEffect)effect).updateSource(objectId);
                }
            }
        }
    }
}

class NecroticPlagueEffect extends ContinuousEffectImpl<NecroticPlagueEffect> {

    private Ability ability1;
    private Ability ability2;
    
	public NecroticPlagueEffect(UUID cardId) {
		super(Duration.WhileOnBattlefield, Outcome.Detriment);
        ability1 = new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new SacrificeSourceEffect());
        ability2 = new DiesTriggeredAbility(new NecroticPlagueEffect2(cardId), false);
		staticText = "Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature.\"  When enchanted creature is put into a graveyard, its controller chooses target creature one of his or her opponents controls. Return {this} from its owner's graveyard to the battlefield attached to that creature.";
	}

	public NecroticPlagueEffect(final NecroticPlagueEffect effect) {
		super(effect);
        this.ability1 = effect.ability1.copy();
        this.ability2 = effect.ability2.copy();
	}

	@Override
	public NecroticPlagueEffect copy() {
		return new NecroticPlagueEffect(this);
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
		Permanent enchantment = game.getPermanent(source.getSourceId());
		if (enchantment != null && enchantment.getAttachedTo() != null) {
			Permanent creature = game.getPermanent(enchantment.getAttachedTo());
			if (creature != null) {
				switch (layer) {
					case AbilityAddingRemovingEffects_6:
						if (sublayer == SubLayer.NA) {
							creature.addAbility(ability1, game);
							creature.addAbility(ability2, game);
						}
						break;
				}
				return true;
			}
		}
		return false;
	}

    public void updateSource(UUID id) {
        for (Effect effect: ability2.getEffects()) {
            if (effect instanceof NecroticPlagueEffect2) {
                ((NecroticPlagueEffect2)effect).updateSource(id);
            }
        }
    }
    
	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public boolean hasLayer(Layer layer) {
		return layer == Layer.AbilityAddingRemovingEffects_6;
	}

}

class NecroticPlagueEffect2 extends OneShotEffect<NecroticPlagueEffect2> {

    private UUID cardId;
    
	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

	static {
		filter.setTargetController(TargetController.OPPONENT);
	}

	public NecroticPlagueEffect2(UUID cardId) {
		super(Outcome.PutCardInPlay);
		this.cardId = cardId;
		staticText = "its controller chooses target creature one of his or her opponents controls. Return {this} from its owner's graveyard to the battlefield attached to that creature.";
	}

	public NecroticPlagueEffect2(final NecroticPlagueEffect2 effect) {
		super(effect);
		this.cardId = effect.cardId;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player controller = game.getPlayer(source.getControllerId());
		if (controller != null) {
			TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                if (controller.chooseTarget(Outcome.Detriment, target, source, game)) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        this.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                        return card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId());
                    }
                }
            }
		}
		return false;
	}

    public void updateSource(UUID id) {
        this.cardId = id;
    }
    
	@Override
	public NecroticPlagueEffect2 copy() {
		return new NecroticPlagueEffect2(this);
	}

}
