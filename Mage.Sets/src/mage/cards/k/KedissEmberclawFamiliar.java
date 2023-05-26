package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KedissEmberclawFamiliar extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a commander you control");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public KedissEmberclawFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a commander you control deals combat damage to an opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new KedissEmberclawFamiliarEffect(), filter, false,
                SetTargetPointer.PLAYER, true, true
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KedissEmberclawFamiliar(final KedissEmberclawFamiliar card) {
        super(card);
    }

    @Override
    public KedissEmberclawFamiliar copy() {
        return new KedissEmberclawFamiliar(this);
    }
}

class KedissEmberclawFamiliarEffect extends OneShotEffect {

    KedissEmberclawFamiliarEffect() {
        super(Outcome.Benefit);
        staticText = "it deals that much damage to each other opponent";
    }

    private KedissEmberclawFamiliarEffect(final KedissEmberclawFamiliarEffect effect) {
        super(effect);
    }

    @Override
    public KedissEmberclawFamiliarEffect copy() {
        return new KedissEmberclawFamiliarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object obj = getValue("sourceId");
        if (!(obj instanceof UUID)) {
            return false;
        }
        UUID sourceId = (UUID) obj;
        obj = getValue("damage");
        if (!(obj instanceof Integer)) {
            return false;
        }
        int damage = (Integer) obj;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            if (playerId.equals(getTargetPointer().getFirst(game, source))) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.damage(damage, sourceId, source, game);
        }
        return true;
    }
}
