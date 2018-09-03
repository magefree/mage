package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.RalIzzetViceroyEmblem;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RalIzzetViceroy extends CardImpl {

    public RalIzzetViceroy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.addAbility(new LoyaltyAbility(
                new LookLibraryAndPickControllerEffect(
                        new StaticValue(2), false, new StaticValue(1),
                        StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false
                ), 1
        ));

        // -3: Ral, Izzet Viceroy deals damage to target creature equal to the total number of instant and sorcery cards you own in exile and in your graveyard.
        Ability ability = new LoyaltyAbility(
                new DamageTargetEffect(new RalIzzetViceroyCount())
                        .setText("{this} deals damage to target creature "
                                + "equal to the total number of instant "
                                + "and sorcery cards you own in exile "
                                + "and in your graveyard"), -3
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: You get an emblem with "Whenever you cast an instant or sorcery spell, this emblem deals 4 damage to any target and you draw two cards."
        this.addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new RalIzzetViceroyEmblem()), -8
        ));
    }

    public RalIzzetViceroy(final RalIzzetViceroy card) {
        super(card);
    }

    @Override
    public RalIzzetViceroy copy() {
        return new RalIzzetViceroy(this);
    }
}

class RalIzzetViceroyCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            return player.getGraveyard().count(
                    StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
            ) + game.getExile().getExileZone(player.getId()).count(
                    StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
            );
        }
        return 0;
    }

    @Override
    public RalIzzetViceroyCount copy() {
        return new RalIzzetViceroyCount();
    }

    @Override
    public String getMessage() {
        return "";
    }
}
