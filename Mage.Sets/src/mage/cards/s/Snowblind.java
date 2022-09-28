package mage.cards.s;

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
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Snowblind extends CardImpl {

    public Snowblind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -X/-Y. If that creature is attacking, X is the number of snow lands defending player controls. Otherwise, X is the number of snow lands its controller controls. Y is equal to X or to enchanted creature's toughness minus 1, whichever is smaller.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(
                SnowblindValue.instanceX, SnowblindValue.instanceY, Duration.WhileOnBattlefield
        ).setText("Enchanted creature gets -X/-Y. If that creature is attacking, " +
                "X is the number of snow lands defending player controls. " +
                "Otherwise, X is the number of snow lands its controller controls. " +
                "Y is equal to X or to enchanted creature's toughness minus 1, whichever is smaller."
        )));
    }

    private Snowblind(final Snowblind card) {
        super(card);
    }

    @Override
    public Snowblind copy() {
        return new Snowblind(this);
    }
}

enum SnowblindValue implements DynamicValue {
    instanceX,
    instanceY;

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePerm = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePerm == null) {
            return 0;
        }
        Permanent permanent = game.getPermanent(sourcePerm.getAttachedTo());
        if (permanent == null) {
            return 0;
        }
        int xValue = 0;
        if (permanent.isAttacking()) {
            xValue = game.getBattlefield().countAll(
                    filter, game.getCombat().getDefendingPlayerId(permanent.getId(), game), game
            );
        } else {
            xValue = game.getBattlefield().countAll(filter, permanent.getControllerId(), game);
        }
        if (this == instanceX) {
            return -xValue;
        }
        return -Math.min(xValue, permanent.getToughness().getValue() - 1);
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
