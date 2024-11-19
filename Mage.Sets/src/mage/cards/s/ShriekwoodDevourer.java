package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShriekwoodDevourer extends CardImpl {

    public ShriekwoodDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you attack with one or more creatures, untap up to X lands, where X is the greatest power among those creatures.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                Zone.BATTLEFIELD, new ShriekwoodDevourerEffect(),
                1, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setTriggerPhrase("Whenever you attack with one or more creatures, "));
    }

    private ShriekwoodDevourer(final ShriekwoodDevourer card) {
        super(card);
    }

    @Override
    public ShriekwoodDevourer copy() {
        return new ShriekwoodDevourer(this);
    }
}

class ShriekwoodDevourerEffect extends OneShotEffect {

    ShriekwoodDevourerEffect() {
        super(Outcome.Benefit);
        staticText = "untap up to X lands, where X is the greatest power among those creatures";
    }

    private ShriekwoodDevourerEffect(final ShriekwoodDevourerEffect effect) {
        super(effect);
    }

    @Override
    public ShriekwoodDevourerEffect copy() {
        return new ShriekwoodDevourerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        return xValue > 0 && new UntapLandsEffect(xValue).apply(game, source);
    }
}
