package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author stravant
 */
public final class RiverSerpent extends CardImpl {

    public RiverSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // River Serpent can't attack unless there are five or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RiverSerpentEffect()));

        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}")));
    }

    private RiverSerpent(final RiverSerpent card) {
        super(card);
    }

    @Override
    public RiverSerpent copy() {
        return new RiverSerpent(this);
    }
}

class RiverSerpentEffect extends RestrictionEffect {
    public RiverSerpentEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless there are five or more cards in your graveyard";
    }

    public RiverSerpentEffect(final RiverSerpentEffect effect) {
        super(effect);
    }

    @Override
    public RiverSerpentEffect copy() {
        return new RiverSerpentEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return new CardsInControllerGraveyardCount().calculate(game, source, this) < 5;
        } else {
            return false;
        }
    }
}