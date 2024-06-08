package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author anonymous
 */
public final class AnimalFriend extends CardImpl {

    public AnimalFriend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature attacks, create a 1/1 green Squirrel creature token. Put a +1/+1 counter on that token for each Aura and Equipment attached to this creature other than Animal Friend."
        Ability ability = new AttacksTriggeredAbility(new AnimalFriendEffect(), false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.AURA)));

    }

    private AnimalFriend(final AnimalFriend card) {
        super(card);
    }

    @Override
    public AnimalFriend copy() {
        return new AnimalFriend(this);
    }
}

class AnimalFriendEffect extends OneShotEffect {

    AnimalFriendEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 green Squirrel creature token." +
                " Put a +1/+1 counter on that token for each Aura and Equipment attached to this creature other than Animal Friend.";
    }

    private AnimalFriendEffect(final AnimalFriendEffect effect) {
        super(effect);
    }

    @Override
    public AnimalFriendEffect copy() {
        return new AnimalFriendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new SquirrelToken());
        boolean result = effect.apply(game, source);
        int auraAmount = new AuraAttachedCount(1).calculate(game, source, this);
        int equipAmount = new EquipmentAttachedCount(1).calculate(game, source, this);
        int xValue = auraAmount + equipAmount;
        if (xValue <= 0 || !result) {
            return result;
        }
        for (UUID id : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(id);
            if (token == null) {
                continue;
            }
            token.addCounters(CounterType.P1P1.createInstance(xValue), source.getControllerId(), source, game);
        }
        return true;
    }

}