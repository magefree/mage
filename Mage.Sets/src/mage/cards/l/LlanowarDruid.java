
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class LlanowarDruid extends CardImpl {

    public LlanowarDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}, Sacrifice Llanowar Druid: Untap all Forests.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new LlanowarDruidEffect(),
                new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private LlanowarDruid(final LlanowarDruid card) {
        super(card);
    }

    @Override
    public LlanowarDruid copy() {
        return new LlanowarDruid(this);
    }
}

class LlanowarDruidEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public LlanowarDruidEffect() {
        super(Outcome.Untap);
        staticText = "Untap all Forests";
    }

    public LlanowarDruidEffect(final LlanowarDruidEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public LlanowarDruidEffect copy() {
        return new LlanowarDruidEffect(this);
    }
}
