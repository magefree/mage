package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.DragonToken2;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonkinBerserker extends CardImpl {

    public DragonkinBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Boast abilities you activate cost {1} less to activate for each Dragon you control.
        this.addAbility(new SimpleStaticAbility(new DragonkinBerserkerEffect()));

        // Boast — {4}{R}: Create a 5/5 red Dragon creature token with flying.
        this.addAbility(new BoastAbility(new CreateTokenEffect(new DragonToken2()), "{4}{R}"));
    }

    private DragonkinBerserker(final DragonkinBerserker card) {
        super(card);
    }

    @Override
    public DragonkinBerserker copy() {
        return new DragonkinBerserker(this);
    }
}

class DragonkinBerserkerEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);

    DragonkinBerserkerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "boast abilities you activate cost {1} less to activate for each Dragon you control";
    }

    private DragonkinBerserkerEffect(DragonkinBerserkerEffect effect) {
        super(effect);
    }

    @Override
    public DragonkinBerserkerEffect copy() {
        return new DragonkinBerserkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, game.getBattlefield().count(
                filter, source.getControllerId(), source, game
        ));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify instanceof BoastAbility;
    }
}
