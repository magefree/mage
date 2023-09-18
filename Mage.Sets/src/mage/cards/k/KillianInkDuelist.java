package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KillianInkDuelist extends CardImpl {

    public KillianInkDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Spells you cast that target a creature cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new KillianInkDuelistEffect()));
    }

    private KillianInkDuelist(final KillianInkDuelist card) {
        super(card);
    }

    @Override
    public KillianInkDuelist copy() {
        return new KillianInkDuelist(this);
    }
}

class KillianInkDuelistEffect extends CostModificationEffectImpl {

    KillianInkDuelistEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.REDUCE_COST);
        staticText = "spells you cast that target a creature cost {2} less to cast";
    }

    private KillianInkDuelistEffect(KillianInkDuelistEffect effect) {
        super(effect);
    }

    @Override
    public KillianInkDuelistEffect copy() {
        return new KillianInkDuelistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        // Bug #7762: https://github.com/magefree/mage/issues/7762
        // Check possible targets for getPlayable
        if (game.inCheckPlayableState()) {
            if (CardUtil.getAllPossibleTargets(abilityToModify, game)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .anyMatch(permanent -> permanent.isCreature(game))) {
                CardUtil.reduceCost(abilityToModify, 2);
            }
        // Check selected targets on actual cast
        } else if (CardUtil.getAllSelectedTargets(abilityToModify, game)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.isCreature(game))) {
            CardUtil.reduceCost(abilityToModify, 2);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId());
    }
}
