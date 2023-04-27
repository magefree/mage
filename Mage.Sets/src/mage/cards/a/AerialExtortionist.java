package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class AerialExtortionist extends CardImpl {
    public AerialExtortionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Aerial Extortionist enters the battlefield or deals combat damage to a player, exile up to one target nonland permanent.
        // For as long as that card remains exiled, its owner may cast it.
        Ability exileAbility = new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new AerialExtortionistExileEffect(),
                false,
                "Whenever Aerial Extortionist enters the battlefield or deals combat damage to a player, ",
                new EntersBattlefieldTriggeredAbility(null, false),
                new DealsCombatDamageToAPlayerTriggeredAbility(null, false)
        );
        exileAbility.addTarget(new TargetNonlandPermanent());
        this.addAbility(exileAbility);

        // Whenever another player casts a spell from anywhere other than their hand, draw a card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1), false, true));
    }

    private AerialExtortionist(final AerialExtortionist card) {
        super(card);
    }

    @Override
    public AerialExtortionist copy() {
        return new AerialExtortionist(this);
    }
}

class AerialExtortionistExileEffect extends OneShotEffect {

    public AerialExtortionistExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile up to one target nonland permanent. " +
                "For as long as that card remains exiled, its owner may cast it";
    }

    public AerialExtortionistExileEffect(final AerialExtortionistExileEffect effect) {
        super(effect);
    }

    @Override
    public AerialExtortionistExileEffect copy() {
        return new AerialExtortionistExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPermanent == null) {
            return false;
        }

        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, targetPermanent,
                TargetController.OWNER, Duration.Custom, false, false, true);
    }
}