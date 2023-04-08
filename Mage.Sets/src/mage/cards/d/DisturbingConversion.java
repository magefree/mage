package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisturbingConversion extends CardImpl {

    public DisturbingConversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Disturbing Conversion enters the battlefield, each player mills two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(2, TargetController.EACH_PLAYER)
        ));

        // Enchanted creature gets -X/-0, where X is the number of cards in its controller's graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(
                DisturbingConversionValue.instance, StaticValue.get(0)
        )));
    }

    private DisturbingConversion(final DisturbingConversion card) {
        super(card);
    }

    @Override
    public DisturbingConversion copy() {
        return new DisturbingConversion(this);
    }
}

enum DisturbingConversionValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return -Optional
                .ofNullable(sourceAbility.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(HashSet::size)
                .orElse(0);
    }

    @Override
    public DisturbingConversionValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of cards in its controller's graveyard";
    }

    @Override
    public String toString() {
        return "-X";
    }
}
