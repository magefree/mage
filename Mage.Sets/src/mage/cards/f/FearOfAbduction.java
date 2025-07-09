package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 * @author Cguy7777
 */
public final class FearOfAbduction extends CardImpl {

    public FearOfAbduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, exile a creature you control.
        this.getSpellAbility().addCost(
                new ExileTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_A_CREATURE)));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Fear of Abduction enters, exile target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FearOfAbductionExileEffect());
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // When Fear of Abduction leaves the battlefield, put each card exiled with it into its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new FearOfAbductionReturnEffect()));
    }

    private FearOfAbduction(final FearOfAbduction card) {
        super(card);
    }

    @Override
    public FearOfAbduction copy() {
        return new FearOfAbduction(this);
    }
}

class FearOfAbductionExileEffect extends OneShotEffect {

    FearOfAbductionExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls";
    }

    private FearOfAbductionExileEffect(final FearOfAbductionExileEffect effect) {
        super(effect);
    }

    @Override
    public FearOfAbductionExileEffect copy() {
        return new FearOfAbductionExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            return new ExileTargetEffect(
                    // Offset -1 so that opponent's creature is exiled to the same exile zone as your creature
                    CardUtil.getExileZoneId(game, source, -1),
                    CardUtil.getSourceName(game, source))
                    .apply(game, source);
        }
        return false;
    }
}

class FearOfAbductionReturnEffect extends OneShotEffect {

    FearOfAbductionReturnEffect() {
        super(Outcome.Neutral);
        this.staticText = "put each card exiled with it into its owner's hand";
    }

    private FearOfAbductionReturnEffect(final FearOfAbductionReturnEffect effect) {
        super(effect);
    }

    @Override
    public FearOfAbductionReturnEffect copy() {
        return new FearOfAbductionReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Offset -2 since Fear of Abduction has left the battlefield since last time
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -2));
            if (exileZone != null) {
                controller.moveCards(exileZone, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
