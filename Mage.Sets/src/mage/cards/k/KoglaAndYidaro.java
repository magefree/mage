package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KoglaAndYidaro extends CardImpl {

    public KoglaAndYidaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Kogla and Yidaro enters the battlefield, choose one --
        // * It gains trample and haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains trample"));
        ability.addEffect(new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste until end of turn"));

        // * It fights target creature you don't control.
        ability.addMode(new Mode(new FightTargetSourceEffect().setText("it fights target creature you don't control"))
                .addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL)));
        this.addAbility(ability);

        // {2}{R}{G}, Discard Kogla and Yidaro: Destroy up to one target artifact or enchantment. Shuffle Kogla and Yidaro into your library from your graveyard, then draw a card.
        ability = new SimpleActivatedAbility(Zone.HAND, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{R}{G}"));
        ability.addCost(new DiscardSourceCost());
        ability.addEffect(new KoglaAndYidaroEffect());
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private KoglaAndYidaro(final KoglaAndYidaro card) {
        super(card);
    }

    @Override
    public KoglaAndYidaro copy() {
        return new KoglaAndYidaro(this);
    }
}

class KoglaAndYidaroEffect extends OneShotEffect {

    KoglaAndYidaroEffect() {
        super(Outcome.Benefit);
        staticText = "Shuffle {this} into your library from your graveyard";
    }

    private KoglaAndYidaroEffect(final KoglaAndYidaroEffect effect) {
        super(effect);
    }

    @Override
    public KoglaAndYidaroEffect copy() {
        return new KoglaAndYidaroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObjectReference mor = new MageObjectReference(source, 1);
        return player != null
                && mor.zoneCounterIsCurrent(game)
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && player.shuffleCardsToLibrary(mor.getCard(game), game, source);
    }
}
