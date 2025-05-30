package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author Jmlundeen
 */
public final class AfterlifeFromTheLoam extends CardImpl {

    public AfterlifeFromTheLoam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}{B}");
        

        // Delve
        this.addAbility(new DelveAbility(false));

        // For each player, choose up to one target creature card in that player's graveyard. Put those cards onto the battlefield under your control. They're Zombies in addition to their other types.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("For each player, choose up to one target creature card in that player's graveyard. Put those cards onto the battlefield under your control"));
        this.getSpellAbility().addEffect(new AddCardSubTypeTargetEffect(SubType.ZOMBIE, Duration.WhileOnBattlefield)
                .setTargetPointer(new EachTargetPointer())
                .setText("they're Zombies in addition to their other types"));
        this.getSpellAbility().setTargetAdjuster(AfterlifeFromTheLoamAdjuster.instance);
    }

    private AfterlifeFromTheLoam(final AfterlifeFromTheLoam card) {
        super(card);
    }

    @Override
    public AfterlifeFromTheLoam copy() {
        return new AfterlifeFromTheLoam(this);
    }
}

enum AfterlifeFromTheLoamAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            String playerName = ability.isControlledBy(playerId) ? "your" : player.getName() + "'s";
            FilterCreatureCard filter = new FilterCreatureCard("creature card in " + playerName + " graveyard");
            filter.add(new OwnerIdPredicate(playerId));
            ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        }
    }
}