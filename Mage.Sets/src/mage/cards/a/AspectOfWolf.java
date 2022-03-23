package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class AspectOfWolf extends CardImpl {

    public AspectOfWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature gets +X/+Y, where X is half the number of Forests you control, rounded down, and Y is half the number of Forests you control, rounded up.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(
                AspectOfWolfValue.UP, AspectOfWolfValue.DOWN, Duration.WhileOnBattlefield
        ).setText("enchanted creature gets +X/+Y, where X is half the number of Forests you control, " +
                "rounded down, and Y is half the number of Forests you control, rounded up")));
    }

    private AspectOfWolf(final AspectOfWolf card) {
        super(card);
    }

    @Override
    public AspectOfWolf copy() {
        return new AspectOfWolf(this);
    }
}

enum AspectOfWolfValue implements DynamicValue {
    UP(true), DOWN(false);

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);
    private final boolean up;

    AspectOfWolfValue(boolean up) {
        this.up = up;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int forestCount = game.getBattlefield().count(
                filter, sourceAbility.getControllerId(), sourceAbility, game
        );
        return forestCount / 2 + (up ? forestCount % 2 : 0);
    }

    @Override
    public AspectOfWolfValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return up ? "X" : "Y";
    }

    @Override
    public String getMessage() {
        return "half the number of Forests you control, rounded " + (up ? "up" : "down");
    }
}
