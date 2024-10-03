package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvaderParasite extends CardImpl {

    private static final FilterPermanent filter
            = new FilterLandPermanent("a land an opponent controls with the same name as the exiled card");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(InvaderParasitePredicate.instance);
    }

    public InvaderParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Imprint - When Invader Parasite enters the battlefield, exile target land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.IMPRINT));

        // Whenever a land with the same name as the exiled card enters the battlefield under an opponent's control, Invader Parasite deals 2 damage to that player.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"),
                filter, false, SetTargetPointer.PLAYER
        ));
    }

    private InvaderParasite(final InvaderParasite card) {
        super(card);
    }

    @Override
    public InvaderParasite copy() {
        return new InvaderParasite(this);
    }
}

enum InvaderParasitePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(game)
                .map(Game::getExile)
                .map(e -> e.getExileZone(CardUtil.getExileZoneId(game, input.getSource())))
                .filter(e -> !e.isEmpty())
                .map(e -> e.getCards(game))
                .map(Collection::stream)
                .map(s -> s.anyMatch(card -> card.sharesName(input.getObject(), game)))
                .orElse(false);
    }
}
