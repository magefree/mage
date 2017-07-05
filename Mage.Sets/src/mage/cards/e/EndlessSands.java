package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

public class EndlessSands extends CardImpl {

    public EndlessSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add("Desert");
                
        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        
        // {2}, {T}: Exile target creature you control.
        Ability exileAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(this.getId(), this.getIdName()), new ManaCostsImpl("{2}"));
        exileAbility.addCost(new TapSourceCost());
        exileAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(exileAbility);
        
        // {4}, {T}, Sacrifice Endless Sands: Return each creature card exiled with Endless Sands to the battlefield under its owner’s control.
        Ability returnAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromExileToOwnersControlEffect(this.getId()),  new ManaCostsImpl("{4}"));
        returnAbility.addCost(new TapSourceCost());
        returnAbility.addCost(new SacrificeSourceCost());
        this.addAbility(returnAbility);
    }

    public EndlessSands(final EndlessSands card) {
        super(card);
    }

    @Override
    public EndlessSands copy() {
        return new EndlessSands(this);
    }
}

class ReturnFromExileToOwnersControlEffect extends OneShotEffect {

    private UUID exileId;

    public ReturnFromExileToOwnersControlEffect(UUID exileId) {
        super(Outcome.PutCardInPlay);
        this.exileId = exileId;
        this.setText("Return each creature card exiled with {this} to the battlefield under its owner’s control.");
    }

    public ReturnFromExileToOwnersControlEffect(final ReturnFromExileToOwnersControlEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public ReturnFromExileToOwnersControlEffect copy() {
        return new ReturnFromExileToOwnersControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && exile != null) {
            controller.moveCards(exile.getCards(new FilterCreatureCard(), game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}