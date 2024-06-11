package mage.cards.a;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
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
 * @author grimreap124
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
//        Ability ability = new AttacksTriggeredAbility(new AnimalFriendEffect(), false).setTriggerPhrase("Whenever this creature attacks, ");
//        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(ability, AttachmentType.AURA)));
        this.addAbility(new SimpleStaticAbility(new AnimalFriendEffect()));

    }

    private AnimalFriend(final AnimalFriend card) {
        super(card);
    }

    @Override
    public AnimalFriend copy() {
        return new AnimalFriend(this);
    }
}

class AuraAttachedNotThisCount implements DynamicValue {

    private final UUID ignoreUUID;

    public AuraAttachedNotThisCount(UUID ignoreUUID) {
        this.ignoreUUID = ignoreUUID;
    }

    protected AuraAttachedNotThisCount(final AuraAttachedNotThisCount dynamicValue) {
        this.ignoreUUID = dynamicValue.ignoreUUID;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent p = game.getPermanent(sourceAbility.getSourceId());
        if (p != null) {
            List<UUID> attachments = p.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.hasSubtype(SubType.AURA, game)) {
                    count++;
                }
            }

        }
        return count;
    }

    @Override
    public AuraAttachedNotThisCount copy() {
        return new AuraAttachedNotThisCount(this);
    }

    @Override
    public String getMessage() {
        return "Aura attached to it other than Animal Friend";
    }

}

class AnimalFriendEffect extends GainAbilityWithAttachmentEffect {

    AnimalFriendEffect() {
        super("create a 1/1 green Squirrel creature token." +
                        " Put a +1/+1 counter on that token for each Aura and Equipment attached to this creature other than Animal Friend.",
                (Effect) null, null, null);
    }

    private AnimalFriendEffect(final AnimalFriendEffect effect) {
        super(effect);
    }

    @Override
    public AnimalFriendEffect copy() {
        return new AnimalFriendEffect(this);
    }

    @Override
    protected Ability makeAbility(Game game, Ability source) {
        return new AttacksTriggeredAbility(new AnimalFriendTokenEffect(source.getId()), false).setTriggerPhrase("Whenever this creature attacks, ");
    }
}

class AnimalFriendTokenEffect extends OneShotEffect {

    UUID ignoreUUID;

    AnimalFriendTokenEffect(UUID ignoreUUID) {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 green Squirrel creature token." +
                " Put a +1/+1 counter on that token for each Aura and Equipment attached to this creature other than Animal Friend.";
        this.ignoreUUID = ignoreUUID;
    }

    private AnimalFriendTokenEffect(final AnimalFriendTokenEffect effect) {
        super(effect);
        this.ignoreUUID = effect.ignoreUUID;
    }

    @Override
    public AnimalFriendTokenEffect copy() {
        return new AnimalFriendTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new SquirrelToken());
        boolean result = effect.apply(game, source);
        int auraAmount = new AuraAttachedNotThisCount(ignoreUUID).calculate(game, source, this);
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