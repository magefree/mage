package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class WickedRoleToken extends TokenImpl {

    public WickedRoleToken() {
        super("Wicked", "Wicked Role token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.ROLE);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature gets +1/+1
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));

        // When this Aura is put into a graveyard from the battlefield, each opponent loses 1 life."
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new LoseLifeOpponentsEffect(1))
                .setTriggerPhrase("When this Aura is put into a graveyard from the battlefield, "));
    }

    private WickedRoleToken(final WickedRoleToken token) {
        super(token);
    }

    public WickedRoleToken copy() {
        return new WickedRoleToken(this);
    }
}
