package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ColdStorage extends CardImpl {

    public ColdStorage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}: Exile target creature you control.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new GenericManaCost(3));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Sacrifice Cold Storage: Return each creature card exiled with Cold Storage to the battlefield under your control.
        this.addAbility(new SimpleActivatedAbility(new ColdStorageEffect(), new SacrificeSourceCost()));
    }

    private ColdStorage(final ColdStorage card) {
        super(card);
    }

    @Override
    public ColdStorage copy() {
        return new ColdStorage(this);
    }
}

class ColdStorageEffect extends OneShotEffect {

    ColdStorageEffect() {
        super(Outcome.Benefit);
        staticText = "return each creature card exiled with {this} to the battlefield under your control";
    }

    private ColdStorageEffect(final ColdStorageEffect effect) {
        super(effect);
    }

    @Override
    public ColdStorageEffect copy() {
        return new ColdStorageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null
                && exileZone != null
                && !exileZone.isEmpty()
                && player.moveCards(exileZone, Zone.BATTLEFIELD, source, game);
    }
}
