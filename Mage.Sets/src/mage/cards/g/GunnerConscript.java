package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JunkToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class GunnerConscript extends CardImpl {

    public GunnerConscript(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Gunner Conscript gets +1/+1 for each Aura and Equipment attached to it.
        DynamicValue totalAmount = new AdditiveDynamicValue(new AuraAttachedCount(), new EquipmentAttachedCount());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(totalAmount, totalAmount, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each Aura and Equipment attached to it")));

        // When Gunner Conscript dies, if it was enchanted, create a Junk token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new CreateTokenEffect(new JunkToken())), GunnerConscriptEnchantedCondition.instance,
                "When Gunner Conscript dies, if it was enchanted, create a Junk token."));

        // When Gunner Conscript dies, if it was equipped, create a Junk token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new CreateTokenEffect(new JunkToken())), GunnerConscriptEquippedCondition.instance,
                "When Gunner Conscript dies, if it was equipped, create a Junk token."));
    }

    private GunnerConscript(final GunnerConscript card) {
        super(card);
    }

    @Override
    public GunnerConscript copy() {
        return new GunnerConscript(this);
    }
}


// Derived from KollTheForgemaster
// Custom predicates call getPermanentOrLKIBattlefield (needed for the death trigger to work correctly)
enum GunnerConscriptEnchantedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        return sourcePermanent != null && sourcePermanent.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.isEnchantment(game));
    }
}

enum GunnerConscriptEquippedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        return sourcePermanent != null && sourcePermanent.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game));
    }
}
