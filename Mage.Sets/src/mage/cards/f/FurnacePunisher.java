package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class FurnacePunisher extends CardImpl {

    public FurnacePunisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Menace
        this.addAbility(new MenaceAbility(false));

        //At the beginning of each player’s upkeep, Furnace Punisher deals 2 damage to that player unless they control
        //two or more basic lands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FurnacePunisherEffect(),
                TargetController.EACH_PLAYER,
                false
        ).setTriggerPhrase("At the beginning of each player's upkeep, "));
    }

    private FurnacePunisher(final FurnacePunisher card) {
        super(card);
    }

    @Override
    public FurnacePunisher copy() {
        return new FurnacePunisher(this);
    }
}

class FurnacePunisherEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    FurnacePunisherEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 2 damage to that player unless they control two or more basic lands";
    }

    private FurnacePunisherEffect(final FurnacePunisherEffect effect) {
        super(effect);
    }

    @Override
    public FurnacePunisherEffect copy() {
        return new FurnacePunisherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            if (!game.getBattlefield().containsControlled(filter, player.getId(), source, game, 2)) {
                player.damage(2, source, game);
            }
            return true;
        }
        return false;
    }
}
