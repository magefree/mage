package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LiesaShroudOfDusk extends CardImpl {

    public LiesaShroudOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Rather than pay {2} for each previous time you've cast this spell from the command zone this game, pay 2 life that many times.
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "Rather than pay {2} for each previous time you've cast this spell " +
                        "from the command zone this game, pay 2 life that many times."
        )));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever a player casts a spell, they lose 2 life.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new LoseLifeTargetEffect(2).setText("they lose 2 life"),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));
    }

    private LiesaShroudOfDusk(final LiesaShroudOfDusk card) {
        super(card);
    }

    @Override
    public LiesaShroudOfDusk copy() {
        return new LiesaShroudOfDusk(this);
    }

    @Override
    public boolean commanderCost(Game game, Ability source, Ability abilityToModify) {
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        int castCount = watcher.getPlaysCount(getMainCard().getId());
        if (castCount > 0) {
            abilityToModify.addCost(new PayLifeCost(2 * castCount));
        }
        return true;
    }
}
