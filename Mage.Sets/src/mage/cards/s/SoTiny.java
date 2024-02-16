package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoTiny extends CardImpl {

    public SoTiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -2/-0. It gets -6/-0 instead as long as its controller has seven or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(
                new BoostEnchantedEffect(SoTinyValue.instance, StaticValue.get(0))
                        .setText("enchanted creature gets -2/-0. It gets -6/-0 instead as long as " +
                                "its controller has seven or more cards in their graveyard")
        ));
    }

    private SoTiny(final SoTiny card) {
        super(card);
    }

    @Override
    public SoTiny copy() {
        return new SoTiny(this);
    }
}

enum SoTinyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());
        if (permanent == null) {
            return -2;
        }
        Player player = game.getPlayer(game.getControllerId(permanent.getAttachedTo()));
        if (player == null || player.getGraveyard().size() < 7) {
            return -2;
        }
        return -6;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
