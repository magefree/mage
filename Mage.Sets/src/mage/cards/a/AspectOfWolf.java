
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author KholdFuzion

 */
public final class AspectOfWolf extends CardImpl {

    public AspectOfWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature gets +X/+Y, where X is half the number of Forests you control, rounded down, and Y is half the number of Forests you control, rounded up.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(new HalfForestsDownCount(), new  HalfForestsUpCount(), Duration.WhileOnBattlefield)));
    }

    private AspectOfWolf(final AspectOfWolf card) {
        super(card);
    }

    @Override
    public AspectOfWolf copy() {
        return new AspectOfWolf(this);
    }
}

class HalfForestsDownCount implements DynamicValue {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) / 2;
    }

    @Override
    public HalfForestsDownCount copy() {
        return new HalfForestsDownCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "half the number of Forests you control, rounded down";
    }
}

class HalfForestsUpCount implements DynamicValue {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = (int) Math.ceil(game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) / 2f);
        return amount;
    }

    @Override
    public HalfForestsUpCount copy() {
        return new HalfForestsUpCount();
    }

    @Override
    public String toString() {
        return "Y";
    }

    @Override
    public String getMessage() {
        return "half the number of Forests you control, rounded up";
    }
}
