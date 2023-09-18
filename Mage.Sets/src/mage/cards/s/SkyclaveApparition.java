package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CustomIllusionToken;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveApparition extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent(
            "nonland, nontoken permanent you don't control with mana value 4 or less"
    );

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public SkyclaveApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Skyclave Apparition enters the battlefield, exile up to one target nonland, nontoken permanent you don't control with converted mana cost 4 or less.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // When Skyclave Apparition leaves the battlefield, the exiled card's owner creates an X/X blue Illusion creature token, where X is the converted mana cost of the exiled card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SkyclaveApparitionEffect(), false));
    }

    private SkyclaveApparition(final SkyclaveApparition card) {
        super(card);
    }

    @Override
    public SkyclaveApparition copy() {
        return new SkyclaveApparition(this);
    }
}

class SkyclaveApparitionEffect extends OneShotEffect {

    SkyclaveApparitionEffect() {
        super(Outcome.Benefit);
        staticText = "the exiled card's owner creates an X/X blue Illusion creature token, " +
                "where X is the mana value of the exiled card";
    }

    private SkyclaveApparitionEffect(final SkyclaveApparitionEffect effect) {
        super(effect);
    }

    @Override
    public SkyclaveApparitionEffect copy() {
        return new SkyclaveApparitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
        ExileZone exile = game.getExile().getExileZone(
                CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game))
        );
        if (exile == null || exile.isEmpty()) {
            return false;
        }
        // From ZNR Release Notes:
        // https://magic.wizards.com/en/articles/archive/feature/zendikar-rising-release-notes-2020-09-10
        // If Skyclave Apparition's first ability exiled more than one card owned by a single player,
        // that player creates a token with power and toughness equal to the sum of those cards' converted mana costs.
        // If the first ability exiled cards owned by more than one player, each of those players creates a token
        // with power and toughness equal to the sum of the converted mana costs of all cards exiled by the first ability.
        Set<UUID> owners = new HashSet<>();
        int totalCMC = exile
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .map(card -> owners.add(card.getOwnerId()) ? card : card)
                .mapToInt(MageObject::getManaValue)
                .sum();
        for (UUID playerId : owners) {
            new CustomIllusionToken(totalCMC).putOntoBattlefield(1, game, source, playerId);
        }
        return true;
    }
}
