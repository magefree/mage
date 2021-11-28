package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class FearOfDeath extends CardImpl {

    public FearOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Fear of Death enters the battlefield, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // Enchanted creature gets -X/-0, where X is the number of cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(FearOfDeathValue.instance, StaticValue.get(0))));
    }

    private FearOfDeath(final FearOfDeath card) {
        super(card);
    }

    @Override
    public FearOfDeath copy() {
        return new FearOfDeath(this);
    }
}

enum FearOfDeathValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }
        return -controller.getGraveyard().size();
    }

    @Override
    public FearOfDeathValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "-X";
    }

    @Override
    public String getMessage() {
        return "the number of cards in your graveyard";
    }
}
