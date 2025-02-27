package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import mage.util.CardUtil;
import java.util.UUID;

/**
 *
 * @author padfoot
 */
public final class TheSoundOfDrums extends CardImpl {

    public TheSoundOfDrums(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature is goaded.
	this.addAbility(new SimpleStaticAbility(new GoadAttachedEffect().setText("enchanted creature is goaded")));
        // If enchanted creature would deal combat damage to a permanent or player, it deals double that damage instead.
	this.addAbility(new SimpleStaticAbility(new TheSoundOfDrumsEffect()));
	// {2}{R}: Return The Sound of Drums from your graveyard to your hand.
	this.addAbility(new SimpleActivatedAbility(
	    Zone.GRAVEYARD, 
	    new ReturnSourceFromGraveyardToHandEffect(), 
	    new ManaCostsImpl<>("{2}{R}")
	));
    }

    private TheSoundOfDrums(final TheSoundOfDrums card) {
        super(card);
    }

    @Override
    public TheSoundOfDrums copy() {
        return new TheSoundOfDrums(this);
    }
}

class TheSoundOfDrumsEffect extends ReplacementEffectImpl {
   
    TheSoundOfDrumsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If enchanted creature would deal combat damage to a permanent or player, it deals double that damage instead";
    }
    
    private TheSoundOfDrumsEffect(final TheSoundOfDrumsEffect effect) {
        super(effect);
    }

    @Override
    public TheSoundOfDrumsEffect copy() {
        return new TheSoundOfDrumsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
	    case DAMAGE_PLAYER:
	    case DAMAGE_PERMANENT:
                return true;
	    default:
		return false;
	}
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
            return enchantment != null
		&& ((DamageEvent) event).isCombatDamage()
		&& enchantment.isAttachedTo(event.getSourceId());
    }
}
