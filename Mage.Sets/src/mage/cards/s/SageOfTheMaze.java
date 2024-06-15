package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author grimreap124
 */
public final class SageOfTheMaze extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Gate you control");

    public SageOfTheMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add two mana in any combination of colors.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(2), new TapSourceCost());
        this.addAbility(ability);

        // {T}: Until end of turn, target land you control becomes an X/X Citizen creature with haste in addition to its other types, where X is twice the number of Gates you control. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new SageOfTheMazeEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        ability.addHint(SageOfTheMazeEffect.hint);
        this.addAbility(ability);

        // Tap an untapped Gate you control: Untap Sage of the Maze.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new TapTargetCost(new TargetControlledPermanent(1, filter)));
        this.addAbility(ability);

    }

    private SageOfTheMaze(final SageOfTheMaze card) {
        super(card);
    }

    @Override
    public SageOfTheMaze copy() {
        return new SageOfTheMaze(this);
    }
}

class SageOfTheMazeEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.GATE, "Gates you control");

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    static final Hint hint = new ValueHint("Gates you control", xValue);

    SageOfTheMazeEffect() {
        super(Outcome.Benefit);
        staticText = "Until end of turn, target land you control becomes an X/X Citizen creature with haste in addition to its other types, where X is twice the number of Gates you control.";
    }

    private SageOfTheMazeEffect(final SageOfTheMazeEffect effect) {
        super(effect);
    }

    @Override
    public SageOfTheMazeEffect copy() {
        return new SageOfTheMazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int pt = xValue.calculate(game, source, this);
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(pt, pt, "")
                        .withSubType(SubType.CITIZEN)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ), source);
        return true;
    }
}
