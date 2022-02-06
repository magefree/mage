package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrashingMudspawn extends CardImpl {

    public ThrashingMudspawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Thrashing Mudspawn is dealt damage, you lose that much life.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new ThrashingMudspawnEffect(), false, false
        );
        this.addAbility(ability);

        // Morph {1}{B}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{1}{B}{B}")));

    }

    private ThrashingMudspawn(final ThrashingMudspawn card) {
        super(card);
    }

    @Override
    public ThrashingMudspawn copy() {
        return new ThrashingMudspawn(this);
    }
}

class ThrashingMudspawnEffect extends OneShotEffect {

    public ThrashingMudspawnEffect() {
        super(Outcome.Damage);
        this.staticText = "you lose that much life";
    }

    public ThrashingMudspawnEffect(final ThrashingMudspawnEffect effect) {
        super(effect);
    }

    @Override
    public ThrashingMudspawnEffect copy() {
        return new ThrashingMudspawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) getValue("damage");
        Player player = game.getPlayer(source.getControllerId());
        if (amount == null || amount < 1 || player == null) {
            return false;
        }
        player.loseLife(amount, game, source, false);
        return true;
    }
}
