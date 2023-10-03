package mage.cards.e;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ElderSpawn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ElderSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}{U}");
        this.subtype.add(SubType.SPAWN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, unless you sacrifice an Island, sacrifice Elder Spawn and it deals 6 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ElderSpawnEffect(), TargetController.YOU, false));

        // Elder Spawn can't be blocked by red creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private ElderSpawn(final ElderSpawn card) {
        super(card);
    }

    @Override
    public ElderSpawn copy() {
        return new ElderSpawn(this);
    }
}

class ElderSpawnEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an Island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public ElderSpawnEffect() {
        super(Outcome.Sacrifice);
        staticText = "unless you sacrifice an Island, sacrifice {this} and it deals 6 damage to you";
    }

    private ElderSpawnEffect(final ElderSpawnEffect effect) {
        super(effect);
    }

    @Override
    public ElderSpawnEffect copy() {
        return new ElderSpawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
            SacrificeTargetCost cost = new SacrificeTargetCost(target);
            if (!controller.chooseUse(Outcome.AIDontUseIt, "Sacrifice an Island?", source, game)
                    || !cost.canPay(source, source, source.getControllerId(), game)
                    || !cost.pay(source, game, source, source.getControllerId(), true)) {
                sourcePermanent.sacrifice(source, game);
                controller.damage(6, sourcePermanent.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}
