package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author themogwi
 */
public final class Plaguecrafter extends CardImpl {

    public Plaguecrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Plaguecrafter enters the battlefield.
        //   Each player sacrifices a creature or planeswalker.
        //   Each player who can't discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PlaguecrafterEffect()));
    }

    public Plaguecrafter(final Plaguecrafter card) {
        super(card);
    }

    @Override
    public Plaguecrafter copy() {
        return new Plaguecrafter(this);
    }
}


class PlaguecrafterEffect extends OneShotEffect {

    public PlaguecrafterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player sacrifices a creature or planeswalker. "
                + "Each player who can't discards a card.";
    }

    public PlaguecrafterEffect(final PlaguecrafterEffect effect) {
        super(effect);
    }

    @Override
    public PlaguecrafterEffect copy() {
        return new PlaguecrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getPlayers().forEach((playerId, player) -> {
            if (!(player == null)) {
                FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();
                filter.add(new ControllerIdPredicate(playerId));
                if (game.getBattlefield().getActivePermanents(
                        filter, source.getControllerId(), game
                ).isEmpty()) {
                    Effect discardEffect = new DiscardTargetEffect(1);
                    discardEffect.setTargetPointer(new FixedTarget(playerId, game));
                    discardEffect.apply(game, source);
                } else {
                    Effect effect = new SacrificeEffect(
                            StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A, 1, null
                    );
                    effect.setTargetPointer(new FixedTarget(playerId, game));
                    effect.apply(game, source);
                }
            }
        });
        return true;
    }
}