
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MinionOfLeshrac extends CardImpl {
    
    private static final FilterPermanent filterCreatureOrLand = new FilterPermanent("creature or land");
    
    static {
        filterCreatureOrLand.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }
    
    public MinionOfLeshrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // At the beginning of your upkeep, Minion of Leshrac deals 5 damage to you unless you sacrifice a creature other than Minion of Leshrac. If Minion of Leshrac deals damage to you this way, tap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MinionLeshracEffect(), TargetController.YOU, false));

        // {tap}: Destroy target creature or land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filterCreatureOrLand));
        this.addAbility(ability);
        
    }
    
    public MinionOfLeshrac(final MinionOfLeshrac card) {
        super(card);
    }
    
    @Override
    public MinionOfLeshrac copy() {
        return new MinionOfLeshrac(this);
    }
}

class MinionLeshracEffect extends OneShotEffect {
    
    public MinionLeshracEffect() {
        super(Outcome.Sacrifice);
        staticText = "{this} deals 5 damage to you unless you sacrifice a creature other than {this}. If {this} deals damage to you this way, tap it";
    }
    
    public MinionLeshracEffect(final MinionLeshracEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent minionLeshrac = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && minionLeshrac != null) {
            FilterControlledPermanent filterCreature = new FilterControlledPermanent();
            filterCreature.add(new CardTypePredicate(CardType.CREATURE));
            filterCreature.add(new AnotherPredicate());
            TargetControlledPermanent target = new TargetControlledPermanent(filterCreature);
            SacrificeTargetCost cost = new SacrificeTargetCost(target);
            if (controller.chooseUse(Outcome.AIDontUseIt, "Do you wish to sacrifice another creature to prevent the 5 damage to you?", source, game)
                    && cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                    && cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
                return true;
            }
            if (controller.damage(5, minionLeshrac.getId(), game, false, true) > 0) {
                minionLeshrac.tap(game);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public MinionLeshracEffect copy() {
        return new MinionLeshracEffect(this);
    }
}
