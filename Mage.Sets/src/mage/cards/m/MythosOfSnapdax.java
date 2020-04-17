package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * 
 * @author Emigara
 *
 */
public class MythosOfSnapdax extends CardImpl{

	public MythosOfSnapdax(UUID ownerId, CardSetInfo setInfo) {
		super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
		
		//Each player chooses an artifact, a creature, an enchantment, and a planeswalker from among the nonland permanents they control, then sacrifices the rest. If {B}{R} was spent to cast this spell, you choose the permanents for each player instead.
		this.getSpellAbility().addEffect(new MythosOfSnapdaxEffect());
		this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
	}
	
	private MythosOfSnapdax(final MythosOfSnapdax card) {
        super(card);
    }
	
	public MythosOfSnapdax copy() {
		return new MythosOfSnapdax(this);
	}
	
	

}

class MythosOfSnapdaxEffect extends OneShotEffect {
	
	private static final Condition condition = new CompoundCondition(
            new ManaWasSpentCondition(ColoredManaSymbol.R),
            new ManaWasSpentCondition(ColoredManaSymbol.B)
    );

    public MythosOfSnapdaxEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, you choose from among the permanents that player controls an artifact, a creature, an enchantment, and a planeswalker. Then each player sacrifices all other nonland permanents they control, If {B}{R} was spent to cast this spell, you choose the permanents for each player instead.";
    }

    public MythosOfSnapdaxEffect(final MythosOfSnapdaxEffect effect) {
        super(effect);
    }

    @Override
    public MythosOfSnapdaxEffect copy() {
        return new MythosOfSnapdaxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Permanent> choosenPermanent = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterArtifactPermanent filterArtifactPermanent = new FilterArtifactPermanent("an artifact of " + player.getName());
                    filterArtifactPermanent.add(new ControllerIdPredicate(playerId));
                    Target target1 = new TargetArtifactPermanent(1, 1, filterArtifactPermanent, true);

                    FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent("a creature of " + player.getName());
                    filterCreaturePermanent.add(new ControllerIdPredicate(playerId));
                    Target target2 = new TargetPermanent(1, 1, filterCreaturePermanent, true);

                    FilterEnchantmentPermanent filterEnchantmentPermanent = new FilterEnchantmentPermanent("an enchantment of " + player.getName());
                    filterEnchantmentPermanent.add(new ControllerIdPredicate(playerId));
                    Target target3 = new TargetPermanent(1, 1, filterEnchantmentPermanent, true);

                    FilterPlaneswalkerPermanent filterPlaneswalkerPermanent = new FilterPlaneswalkerPermanent("a planeswalker of " + player.getName());
                    filterPlaneswalkerPermanent.add(new ControllerIdPredicate(playerId));
                    Target target4 = new TargetPermanent(1, 1, filterPlaneswalkerPermanent, true);

                    //if the mana condition wasn't met
                    if (!condition.apply(game, source)) {
						if (target1.canChoose(source.getSourceId(), controller.getId(), game)) {
							controller.chooseTarget(Outcome.Benefit, target1, source, game);
							Permanent artifact = game.getPermanent(target1.getFirstTarget());
							if (artifact != null) {
								choosenPermanent.add(artifact);
							}
							target1.clearChosen();
						}
						if (target2.canChoose(source.getSourceId(), controller.getId(), game)) {
							controller.chooseTarget(Outcome.Benefit, target2, source, game);
							Permanent creature = game.getPermanent(target2.getFirstTarget());
							if (creature != null) {
								choosenPermanent.add(creature);
							}
							target2.clearChosen();
						}
						if (target3.canChoose(source.getSourceId(), controller.getId(), game)) {
							controller.chooseTarget(Outcome.Benefit, target3, source, game);
							Permanent enchantment = game.getPermanent(target3.getFirstTarget());
							if (enchantment != null) {
								choosenPermanent.add(enchantment);
							}
							target3.clearChosen();
						}
						if (target4.canChoose(source.getSourceId(), controller.getId(), game)) {
							controller.chooseTarget(Outcome.Benefit, target4, source, game);
							Permanent planeswalker = game.getPermanent(target4.getFirstTarget());
							if (planeswalker != null) {
								choosenPermanent.add(planeswalker);
							}
							target4.clearChosen();
						} 
					//if the mana condition was met	
					}else {
						if (target1.canChoose(source.getSourceId(), controller.getId(), game)) {
							player.chooseTarget(Outcome.Benefit, target1, source, game);
							Permanent artifact = game.getPermanent(target1.getFirstTarget());
							if (artifact != null) {
								choosenPermanent.add(artifact);
							}
							target1.clearChosen();
						}
						if (target2.canChoose(source.getSourceId(), controller.getId(), game)) {
							player.chooseTarget(Outcome.Benefit, target2, source, game);
							Permanent creature = game.getPermanent(target2.getFirstTarget());
							if (creature != null) {
								choosenPermanent.add(creature);
							}
							target2.clearChosen();
						}
						if (target3.canChoose(source.getSourceId(), controller.getId(), game)) {
							player.chooseTarget(Outcome.Benefit, target3, source, game);
							Permanent enchantment = game.getPermanent(target3.getFirstTarget());
							if (enchantment != null) {
								choosenPermanent.add(enchantment);
							}
							target3.clearChosen();
						}
						if (target4.canChoose(source.getSourceId(), controller.getId(), game)) {
							player.chooseTarget(Outcome.Benefit, target4, source, game);
							Permanent planeswalker = game.getPermanent(target4.getFirstTarget());
							if (planeswalker != null) {
								choosenPermanent.add(planeswalker);
							}
							target4.clearChosen();
						} 
					}
                }
            }
            // Then each player sacrifices all other nonland permanents they control
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENTS_NON_LAND, playerId, game)) {
                        if (!choosenPermanent.contains(permanent)) {
                            permanent.sacrifice(playerId, game);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
}
