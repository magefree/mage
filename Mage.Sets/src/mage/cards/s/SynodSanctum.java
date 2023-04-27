
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public final class SynodSanctum extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SynodSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {tap}: Exile target permanent you control.
        SynodSanctumEffect effect = new SynodSanctumEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);

        // {2}, Sacrifice Synod Sanctum: Return all cards exiled with Synod Sanctum to the battlefield under your control.
        SynodSanctumEffect2 effect2 = new SynodSanctumEffect2();
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new ManaCostsImpl<>("{2}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private SynodSanctum(final SynodSanctum card) {
        super(card);
    }

    @Override
    public SynodSanctum copy() {
        return new SynodSanctum(this);
    }
}

class SynodSanctumEffect extends OneShotEffect {

    SynodSanctumEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target permanent you control";
    }

    SynodSanctumEffect(SynodSanctumEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    if (exileZone != null) {
                        controller.moveCardToExileWithInfo(permanent, exileZone, sourceObject.getIdName(), source, game, Zone.BATTLEFIELD, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SynodSanctumEffect copy() {
        return new SynodSanctumEffect(this);
    }
}

class SynodSanctumEffect2 extends OneShotEffect {

    SynodSanctumEffect2() {
        super(Outcome.Benefit);
        staticText = "Return all cards exiled with {this} to the battlefield under your control";
    }

    SynodSanctumEffect2(SynodSanctumEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return true;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(exileZone, Zone.BATTLEFIELD, source, game);
        }
        return false;
    }

    @Override
    public SynodSanctumEffect2 copy() {
        return new SynodSanctumEffect2(this);
    }
}
