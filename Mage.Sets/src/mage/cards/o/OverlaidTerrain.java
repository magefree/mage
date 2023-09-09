package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OverlaidTerrain extends CardImpl {

    public OverlaidTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // As Overlaid Terrain enters the battlefield, sacrifice all lands you control.
        this.addAbility(new AsEntersBattlefieldAbility(new SacrificeAllLandEffect()));

        // Lands you control have "{T}: Add two mana of any one color."
        SimpleManaAbility manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(manaAbility, Duration.WhileOnBattlefield, new FilterLandPermanent(), false)));
    }

    private OverlaidTerrain(final OverlaidTerrain card) {
        super(card);
    }

    @Override
    public OverlaidTerrain copy() {
        return new OverlaidTerrain(this);
    }
}

class SacrificeAllLandEffect extends OneShotEffect {

    SacrificeAllLandEffect() {
        super(Outcome.Detriment);
        staticText = "sacrifice all lands you control";
    }

    private SacrificeAllLandEffect(final SacrificeAllLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), source.getControllerId(), game)) {
                permanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public SacrificeAllLandEffect copy() {
        return new SacrificeAllLandEffect(this);
    }
}