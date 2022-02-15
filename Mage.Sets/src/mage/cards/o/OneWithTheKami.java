package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OneWithTheKami extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("enchanted creature or another modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public OneWithTheKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature or another modified creature you control dies, create X 1/1 colorless Spirit creature tokens, where X is that creature's power.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(
                new SpiritToken(), OneWithTheKamiValue.instance
        ), false, filter));
    }

    private OneWithTheKami(final OneWithTheKami card) {
        super(card);
    }

    @Override
    public OneWithTheKami copy() {
        return new OneWithTheKami(this);
    }
}

enum OneWithTheKamiValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("creatureDied");
        return permanent != null ? permanent.getPower().getValue() : 0;
    }

    @Override
    public OneWithTheKamiValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that creature's power";
    }

    @Override
    public String toString() {
        return "X";
    }
}
