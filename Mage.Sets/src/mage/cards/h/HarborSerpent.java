package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public final class HarborSerpent extends CardImpl {

    public HarborSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Islandwalk (This creature is unblockable as long as defending player controls an Island.)
        this.addAbility(new IslandwalkAbility());

        // Harbor Serpent can't attack unless there are five or more Islands on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HarborSerpentEffect()));
    }

    private HarborSerpent(final HarborSerpent card) {
        super(card);
    }

    @Override
    public HarborSerpent copy() {
        return new HarborSerpent(this);
    }
}

class HarborSerpentEffect extends RestrictionEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.ISLAND, "Island");

    public HarborSerpentEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless there are five or more Islands on the battlefield";
    }

    public HarborSerpentEffect(final HarborSerpentEffect effect) {
        super(effect);
    }

    @Override
    public HarborSerpentEffect copy() {
        return new HarborSerpentEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId()) &&
                game.getBattlefield().count(filter, source.getControllerId(), source, game) < 5;
    }
}
