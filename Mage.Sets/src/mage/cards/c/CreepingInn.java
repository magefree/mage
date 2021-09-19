package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class CreepingInn extends CardImpl {

    public CreepingInn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);
        this.color.setBlack(true);
        this.transformable = true;
        this.nightCard = true;

        // Whenever Creeping Inn attacks, you may exile a creature card from your graveyard.
        // If you do, each opponent loses X life and you gain X life,
        // where X is the number of creature cards exiled with Creeping Inn.
        this.addAbility(new AttacksTriggeredAbility(new CreepingInnEffect()));

        // {4}: Creeping Inn phases out.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhaseOutSourceEffect(), new ManaCostsImpl("{4}")));
    }

    private CreepingInn(final CreepingInn card) {
        super(card);
    }

    @Override
    public CreepingInn copy() {
        return new CreepingInn(this);
    }
}

class CreepingInnEffect extends OneShotEffect {

    public CreepingInnEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile a creature card from your graveyard. " +
                "If you do, each opponent loses X life and you gain X life, " +
                "where X is the number of creature cards exiled with Creeping Inn.";
    }

    public CreepingInnEffect(final CreepingInnEffect effect) {
        super(effect);
    }

    @Override
    public CreepingInnEffect copy() {
        return new CreepingInnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player != null && permanent != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source);
            TargetCardInGraveyard target = new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                if (player.choose(Outcome.Exile, target, source.getId(), game)) {
                    Card cardChosen = game.getCard(target.getFirstTarget());
                    if (cardChosen != null) {
                        int lifeAmount = 0;
                        player.moveCardsToExile(cardChosen, source, game, true, exileId, permanent.getName());
                        ExileZone exile = game.getExile().getExileZone(exileId);
                        if (exile != null) {
                            for (UUID cardId : exile) {
                                lifeAmount++;
                            }
                        }
                        for (UUID playerId : game.getOpponents(source.getControllerId())) {
                            game.getPlayer(playerId).loseLife(lifeAmount, game, source, false);
                        }
                        player.gainLife(lifeAmount, game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
