package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Domestication extends CardImpl {

    public Domestication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        this.addAbility(new EnchantAbility(auraTarget));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(new ControlEnchantedEffect()));

        // At the beginning of your end step, if enchanted creature's power is 4 or greater, sacrifice Domestication.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(DomesticationCondition.instance));
    }

    private Domestication(final Domestication card) {
        super(card);
    }

    @Override
    public Domestication copy() {
        return new Domestication(this);
    }
}

enum DomesticationCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(x -> x >= 4)
                .isPresent();
    }

    @Override
    public String toString() {
        return "enchanted creature's power is 4 or greater";
    }
}
