package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderworldSentinel extends CardImpl {

    public UnderworldSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Underworld Sentinel attacks, exile target creature card from your graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // When Underworld Sentinel dies, put all cards exiled with it onto the battlefield.
        this.addAbility(new DiesSourceTriggeredAbility(new UnderworldSentinelEffect()));
    }

    private UnderworldSentinel(final UnderworldSentinel card) {
        super(card);
    }

    @Override
    public UnderworldSentinel copy() {
        return new UnderworldSentinel(this);
    }
}

class UnderworldSentinelEffect extends OneShotEffect {

    UnderworldSentinelEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "put all cards exiled with it onto the battlefield";
    }

    private UnderworldSentinelEffect(final UnderworldSentinelEffect effect) {
        super(effect);
    }

    @Override
    public UnderworldSentinelEffect copy() {
        return new UnderworldSentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(
                CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter())
        );
        return exileZone != null && controller.moveCards(exileZone, Zone.BATTLEFIELD, source, game);
    }
}
