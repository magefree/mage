
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CataclysmicGearhulk extends CardImpl {

    public CataclysmicGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Cataclysmic Gearhulk enters the battlefield, each player chooses from among the non-land permanents they control an artifact, a creature,
        // an enchantment, and a planeswalker, then sacrifices the rest.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CataclysmicGearhulkEffect(), false));
    }

    private CataclysmicGearhulk(final CataclysmicGearhulk card) {
        super(card);
    }

    @Override
    public CataclysmicGearhulk copy() {
        return new CataclysmicGearhulk(this);
    }
}

class CataclysmicGearhulkEffect extends OneShotEffect {

    private static final FilterControlledArtifactPermanent filterArtifact = new FilterControlledArtifactPermanent("artifact");
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("creature");
    private static final FilterControlledEnchantmentPermanent filterEnchantment = new FilterControlledEnchantmentPermanent("enchantment");
    private static final FilterControlledPlaneswalkerPermanent filterPlaneswalker = new FilterControlledPlaneswalkerPermanent("planeswalker");

    static {
        filterArtifact.add(Predicates.and(
                CardType.ARTIFACT.getPredicate(),
                Predicates.not(CardType.LAND.getPredicate())));
        filterCreature.add(Predicates.and(
                CardType.CREATURE.getPredicate(),
                Predicates.not(CardType.LAND.getPredicate())));
        filterEnchantment.add(Predicates.and(
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.not(CardType.LAND.getPredicate())));
        filterPlaneswalker.add(Predicates.and(
                CardType.PLANESWALKER.getPredicate(),
                Predicates.not(CardType.LAND.getPredicate())));
    }

    public CataclysmicGearhulkEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "each player chooses an artifact, a creature, an enchantment, and a planeswalker " +
                "from among the nonland permanents they control, then sacrifices the rest";
    }

    public CataclysmicGearhulkEffect(CataclysmicGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<>();

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, filterArtifact, true);
            Target target2 = new TargetControlledPermanent(1, 1, filterCreature, true);
            Target target3 = new TargetControlledPermanent(1, 1, filterEnchantment, true);
            Target target4 = new TargetControlledPermanent(1, 1, filterPlaneswalker, true);

            if (target1.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target1.isChosen() && target1.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target1, source, game);
                }
                Permanent artifact = game.getPermanent(target1.getFirstTarget());
                if (artifact != null) {
                    chosen.add(artifact);
                }
                target1.clearChosen();
            }

            if (target2.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target2.isChosen() && target2.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target2, source, game);
                }
                Permanent creature = game.getPermanent(target2.getFirstTarget());
                if (creature != null) {
                    chosen.add(creature);
                }
                target2.clearChosen();
            }

            if (target3.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target3.isChosen() && target3.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target3, source, game);
                }
                Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                if (enchantment != null) {
                    chosen.add(enchantment);
                }
                target3.clearChosen();
            }

            if (target4.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target4.isChosen() && target4.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target4, source, game);
                }
                Permanent planeswalker = game.getPermanent(target4.getFirstTarget());
                if (planeswalker != null) {
                    chosen.add(planeswalker);
                }
                target4.clearChosen();
            }

        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (!chosen.contains(permanent) && !permanent.isLand(game)) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }

    @Override
    public CataclysmicGearhulkEffect copy() {
        return new CataclysmicGearhulkEffect(this);
    }
}
