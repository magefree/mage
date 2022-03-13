package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CruelReality extends CardImpl {

    public CruelReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);
        this.color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        //At the beginning of enchanted player's upkeep, that player sacrifices a creature or planeswalker. If the player can't, they lose 5 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CruelRealityEffect(), TargetController.ENCHANTED, false));
    }

    private CruelReality(final CruelReality card) {
        super(card);
    }

    @Override
    public CruelReality copy() {
        return new CruelReality(this);
    }
}

class CruelRealityEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("creature or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    CruelRealityEffect() {
        super(Outcome.LoseLife);
        staticText = "that player sacrifices a creature or planeswalker. If the player can't, they lose 5 life";
    }

    private CruelRealityEffect(final CruelRealityEffect effect) {
        super(effect);
    }

    @Override
    public CruelRealityEffect copy() {
        return new CruelRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player cursedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (cursedPlayer == null || controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (target.canChoose(cursedPlayer.getId(), source, game)
                && cursedPlayer.choose(Outcome.Sacrifice, target, source, game)) {
            Permanent objectToBeSacrificed = game.getPermanent(target.getFirstTarget());
            if (objectToBeSacrificed != null) {
                if (objectToBeSacrificed.sacrifice(source, game)) {
                    return true;
                }
            }
        }
        cursedPlayer.loseLife(5, game, source, false);
        return true;
    }
}
