package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class HellraiserGoblin extends CardImpl {

    public HellraiserGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control have haste and attack each combat if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES));
        Effect effect = new AttacksIfAbleAllEffect(Duration.WhileOnBattlefield);
        effect.setText("and attack each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private HellraiserGoblin(final HellraiserGoblin card) {
        super(card);
    }

    @Override
    public HellraiserGoblin copy() {
        return new HellraiserGoblin(this);
    }
}

class AttacksIfAbleAllEffect extends RequirementEffect {

    private FilterControlledCreaturePermanent filter;

    public AttacksIfAbleAllEffect(Duration duration) {
        this(duration, new FilterControlledCreaturePermanent());
    }

    public AttacksIfAbleAllEffect(Duration duration, FilterControlledCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
    }

    public AttacksIfAbleAllEffect(final AttacksIfAbleAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public AttacksIfAbleAllEffect copy() {
        return new AttacksIfAbleAllEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return filter.getMessage() + " attack each combat if able";
    }

}
