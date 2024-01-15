package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SavraQueenOfTheGolgari extends CardImpl {

    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("a black creature");
    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("a green creature");
    static {
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SavraQueenOfTheGolgari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice a black creature, you may pay 2 life. If you do, each other player sacrifices a creature.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new DoIfCostPaid(new SavraSacrificeEffect(), new PayLifeCost(2)),
                filterBlack
        ));

        // Whenever you sacrifice a green creature, you may gain 2 life.
        this.addAbility(new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(2),
                filterGreen, TargetController.YOU, SetTargetPointer.NONE, true
        ));
    }

    private SavraQueenOfTheGolgari(final SavraQueenOfTheGolgari card) {
        super(card);
    }

    @Override
    public SavraQueenOfTheGolgari copy() {
        return new SavraQueenOfTheGolgari(this);
    }
}

class SavraSacrificeEffect extends OneShotEffect {

    SavraSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each other player sacrifices a creature";
    }

    private SavraSacrificeEffect(final SavraSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public SavraSacrificeEffect copy() {
        return new SavraSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !playerId.equals(source.getControllerId())) {
                    TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_PERMANENT_CREATURE);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
