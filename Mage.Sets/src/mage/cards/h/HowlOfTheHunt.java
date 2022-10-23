package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlOfTheHunt extends CardImpl {

    public HowlOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Howl of the Hunt enters the battlefield, if enchanted creature is a Wolf or Werewolf, untap that creature.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new UntapEnchantedEffect()),
                HowlOfTheHuntCondition.instance, "When {this} enters the battlefield, " +
                "if enchanted creature is a Wolf or Werewolf, untap that creature."
        ));

        // Enchanted creature gets +2/+2 and has vigilance.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield
        ).setText("and has vigilance"));
        this.addAbility(ability);
    }

    private HowlOfTheHunt(final HowlOfTheHunt card) {
        super(card);
    }

    @Override
    public HowlOfTheHunt copy() {
        return new HowlOfTheHunt(this);
    }
}

enum HowlOfTheHuntCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null) {
            return false;
        }
        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        return creature != null && creature.hasSubtype(SubType.WOLF, game) || creature.hasSubtype(SubType.WEREWOLF, game);
    }

    @Override
    public String toString() {
        return "";
    }
}
