package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

public final class EndlessSands extends CardImpl {

    public EndlessSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Exile target creature you control.
        Ability exileAbility = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new GenericManaCost(2));
        exileAbility.addCost(new TapSourceCost());
        exileAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(exileAbility);

        // {4}, {T}, Sacrifice Endless Sands: Return each creature card exiled with Endless Sands to the battlefield under its owner's control.
        Ability returnAbility = new SimpleActivatedAbility(new EndlessSandsEffect(), new GenericManaCost(4));
        returnAbility.addCost(new TapSourceCost());
        returnAbility.addCost(new SacrificeSourceCost());
        this.addAbility(returnAbility);
    }

    private EndlessSands(final EndlessSands card) {
        super(card);
    }

    @Override
    public EndlessSands copy() {
        return new EndlessSands(this);
    }
}

class EndlessSandsEffect extends OneShotEffect {

    EndlessSandsEffect() {
        super(Outcome.Benefit);
        staticText = "return each creature card exiled with {this} to the battlefield under its owner's control";
    }

    private EndlessSandsEffect(final EndlessSandsEffect effect) {
        super(effect);
    }

    @Override
    public EndlessSandsEffect copy() {
        return new EndlessSandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null && exileZone != null
                && player.moveCards(
                exileZone.getCards(game), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
    }
}
