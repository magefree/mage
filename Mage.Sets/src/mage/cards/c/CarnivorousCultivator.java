package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.PrepareCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class CarnivorousCultivator extends PrepareCard {

    public CarnivorousCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}", "Enroot", new CardType[]{CardType.SORCERY}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Whenever this creature deals combat damage to a player, return target land card from your graveyard to your hand.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Enroot
        // Sorcery {G}
        // Search your library for a land card, put it into your graveyard, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new EnrootEffect());
    }

    private CarnivorousCultivator(final CarnivorousCultivator card) {
        super(card);
    }

    @Override
    public CarnivorousCultivator copy() {
        return new CarnivorousCultivator(this);
    }
}

class EnrootEffect extends SearchEffect {

    private static final FilterCard filter = new FilterLandCard("land card");

    public EnrootEffect() {
        super(new TargetCardInLibrary(filter), Outcome.Neutral);
        staticText = "search your library for a land card, put it into your graveyard, then shuffle";
    }

    private EnrootEffect(final EnrootEffect effect) {
        super(effect);
    }

    @Override
    public EnrootEffect copy() {
        return new EnrootEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.GRAVEYARD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
