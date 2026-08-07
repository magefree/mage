package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 * @author muz
 */
public final class TheLordOfTheEagles extends CardImpl {

    public TheLordOfTheEagles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs {X} less to cast, where X is the total power of creatures you control with flying.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TheLordOfTheEaglesCostReductionEffect()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private TheLordOfTheEagles(final TheLordOfTheEagles card) {
        super(card);
    }

    @Override
    public TheLordOfTheEagles copy() {
        return new TheLordOfTheEagles(this);
    }
}

class TheLordOfTheEaglesCostReductionEffect extends CostModificationEffectImpl {

    TheLordOfTheEaglesCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {X} less to cast, where X is the total power of creatures you control with flying";
    }

    private TheLordOfTheEaglesCostReductionEffect(final TheLordOfTheEaglesCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int totalPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.isCreature(game) && permanent.hasAbility(FlyingAbility.getInstance(), game)) {
                totalPower += permanent.getPower().getValue();
            }

        }
        CardUtil.reduceCost(abilityToModify, totalPower);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility);
    }

    @Override
    public TheLordOfTheEaglesCostReductionEffect copy() {
        return new TheLordOfTheEaglesCostReductionEffect(this);
    }
}
